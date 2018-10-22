package promobusque.ramon.promobusqueapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_bottom_promobusque.*
import promobusque.ramon.promobusqueapp.modelos.Promocao
import promobusque.ramon.promobusqueapp.retrofit.RetrofitInitializer
import promobusque.ramon.promobusqueapp.ui.PromocoesAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.jvm.internal.Intrinsics
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import promobusque.ramon.promobusqueapp.dialogs.ConfiguracaoPromobusqueDialog
import promobusque.ramon.promobusqueapp.modelos.ConfiguracaoPromobusque


const val TAG: String = "Promobusque"

class PromocoesActivity : AppCompatActivity() {

    val bottomNavigationView: BottomNavigationView? = null
    private var mFirebaseAuth: FirebaseAuth? = null
    private var mAuthStateListener: FirebaseAuth.AuthStateListener? = null
    val ANONYMOUS = "anonymous"

    //Choose an arbitrary request code value
    private val RC_SIGN_IN = 1
    private var mUsername: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_promobusque)

        //Seta o bottom navigation
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        mUsername = ANONYMOUS

        //Autenticação do aplicativo
        mFirebaseAuth = FirebaseAuth.getInstance()

        //Implementa o login
        implementaLogin()

        //Busca todas as promoções e preenche o adapter
        preencheAdapter()
    }

    private fun inscreverTopicoFirebase() {
        FirebaseMessaging.getInstance().subscribeToTopic("promobusque")
        val instance = FirebaseInstanceId.getInstance()

        instance.instanceId.addOnSuccessListener{
            Log.d(TAG, "Firebase iniciado.")
        }
    }

    private fun preencheAdapter() {
        val call = RetrofitInitializer().promocaoService().obterPromocoes()

        call.enqueue(object : Callback<List<Promocao>> {
            override fun onResponse(call: Call<List<Promocao>>?, response: Response<List<Promocao>>?) {

                if (response != null && response.isSuccessful) {
                    response?.let {
                        val promocoes: List<Promocao>? = it.body()
                        atualizaAdapter(promocoes!!)
                    }
                } else {
                    Log.e(TAG, "ocorreu um problema na requisição: " + (response?.errorBody() ?: ""))
                }
            }

            override fun onFailure(call: Call<List<Promocao>>?, t: Throwable?) {
                Log.e(TAG, "ocorreu um problema na requisição: " + (t?.message ?: ""))
            }
        })

    }

    fun atualizaAdapter(promocoes: List<Promocao>) {
        list_view_promocoes.adapter = PromocoesAdapter(promocoes, this@PromocoesActivity)
    }

    private fun implementaLogin() {
        //Implementa login
        mAuthStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            //Se o usuário estiver logado, apresenta mensagem
            if (user != null) {
                onSignedInitalize(user.displayName)
                inscreverTopicoFirebase()
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
    }

    private fun onSignedOutCleanup() {
        mUsername = ANONYMOUS
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                abrePromocoesFavoritas()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                abreDialogConfiguracoes()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                message.setText(R.string.title_filters)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun abrePromocoesFavoritas() {
        val intent = Intent(this,  PromocoesFavoritasActivity::class.java)
        startActivity(intent)
    }

    private fun abreDialogConfiguracoes() {
        ConfiguracaoPromobusqueDialog(this, window.decorView as ViewGroup).abre()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menupromobusque, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sign_out_menu -> {
                AuthUI.getInstance().signOut(this)
                return true
            }
            R.id.op_refresh -> {
                preencheAdapter()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }
}
