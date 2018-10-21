package promobusque.ramon.promobusqueapp

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

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

        //FirebaseApp.initializeApp(this)

        mUsername = ANONYMOUS

        //Autenticação do aplicativo
        mFirebaseAuth = FirebaseAuth.getInstance()

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

    private fun onSignedInitalize(displayName: String?) {
        mUsername = displayName
        //attachDatabaseReadListener()
    }

    private fun onSignedOutCleanup() {
        mUsername = ANONYMOUS
        //mMessageAdapter.clear()
        //detachDatabaseReadListener()
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
}
