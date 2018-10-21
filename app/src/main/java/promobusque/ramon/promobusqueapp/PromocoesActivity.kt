package promobusque.ramon.promobusqueapp

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_promocoes.*
import promobusque.ramon.promobusqueapp.modelos.Promocao
import promobusque.ramon.promobusqueapp.retrofit.RetrofitInitializer
import promobusque.ramon.promobusqueapp.ui.PromocoesAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val TAG: String = "Promocoes"

class PromocoesActivity : AppCompatActivity() {


    private var mFirebaseAuth: FirebaseAuth? = null
    private var mAuthStateListener: FirebaseAuth.AuthStateListener? = null

    val ANONYMOUS = "anonymous"

    //Choose an arbitrary request code value
    private val RC_SIGN_IN = 1

    private var mUsername: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promocoes)

        mUsername = ANONYMOUS

        //Autenticação do aplicativo
        mFirebaseAuth = FirebaseAuth.getInstance()

        //Implementa o login
        implementaLogin()

        //Busca todas as promoções e preenche o adapter
        preencheAdapter()
    }

    private fun preencheAdapter() {
        val call = RetrofitInitializer().promocaoService().obterPromocoes()

        call.enqueue(object : Callback<List<Promocao>> {
            override fun onResponse(call: Call<List<Promocao>>?, response: Response<List<Promocao>>?) {

                if (response != null && response.isSuccessful) {
                    response?.let {
                        val promocoes: List<Promocao>? = it.body()
                        setAdapterPromocoes(promocoes!!)
                    }


                } else {
                    Log.e(TAG, "ocorreu um problema na requisição: " + (response?.errorBody() ?: ""))
                }
            }

            private fun setAdapterPromocoes(promocoes: List<Promocao> ) {
                list_view_promocoes.adapter = PromocoesAdapter(promocoes, this@PromocoesActivity)
            }

            override fun onFailure(call: Call<List<Promocao>>?, t: Throwable?) {
                Log.e(TAG, "ocorreu um problema na requisição: " + (t?.message ?: ""))
            }
        })

    }

    private fun implementaLogin() {
        //Implementa login
        mAuthStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            //Se o usuário estiver logado, apresenta mensagem
            if (user != null) {
                onSignedInitalize(user.displayName)
                Toast.makeText(this@PromocoesActivity, "Você conectou-se ao Promobusque!", Toast.LENGTH_SHORT).show()
            } else {
                onSignedOutCleanup()
                startActivityForResult(
                    AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .build(),
                    RC_SIGN_IN
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "Login realizado!", Toast.LENGTH_LONG).show()
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Login cancelado!", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (mAuthStateListener != null) {
            mFirebaseAuth?.removeAuthStateListener(mAuthStateListener!!)
        }
    }

    override fun onResume() {
        super.onResume()
        mFirebaseAuth?.addAuthStateListener(mAuthStateListener!!)
    }

    private fun onSignedInitalize(displayName: String?) {
        mUsername = displayName
        //attachDatabaseReadListener()
    }

    private fun onSignedOutCleanup() {
        mUsername = ANONYMOUS
        //mMessageAdapter.clear()
        //detachDatabaseReadListener()
    }
}
