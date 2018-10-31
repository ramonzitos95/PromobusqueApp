package promobusque.ramon.promobusqueapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_bottom_promobusque.*
import promobusque.ramon.promobusqueapp.fragments.ConfiguracaoFragment
import promobusque.ramon.promobusqueapp.fragments.PromocaoFavoritaFragment
import promobusque.ramon.promobusqueapp.fragments.PromocoesFragment


class PromocoesActivity : AppCompatActivity() {

    val TAG: String = "Promobusque"
    private var mFirebaseAuth: FirebaseAuth? = null
    private var mAuthStateListener: FirebaseAuth.AuthStateListener? = null
    val ANONYMOUS = "anonymous"
    private val FRAGMENT_PROMOCOES = "FRAGMENT_PROMOCOES"
    private val FRAGMENT_PROMOCOES_FAVORITAS = "FRAGMENT_PROMOCOES_FAVORITAS"
    private val FRAGMENT_CONFIG = "FRAGMENT_CONFIG"

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

        executaFragmentPromocoes()

        customizarActionBar()
    }

    fun customizarActionBar(){
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeButtonEnabled(true)
        actionBar?.title = "Promobusque"
    }

    private fun inscreverTopicoFirebase() {
        FirebaseMessaging.getInstance().subscribeToTopic("promobusque")
        val instance = FirebaseInstanceId.getInstance()

        instance.instanceId.addOnSuccessListener{
            Log.d(TAG, "Firebase iniciado.")
        }
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
            R.id.navigation_promocoes -> {
                executaFragmentPromocoes()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_promocoes_favoritas -> {
                val frag = PromocaoFavoritaFragment()
                manageFragment(frag, FRAGMENT_PROMOCOES_FAVORITAS)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_configuracoes -> {
                val frag = ConfiguracaoFragment()
                manageFragment(frag , FRAGMENT_CONFIG)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun executaFragmentPromocoes() {
        val frag = PromocoesFragment()
        manageFragment(frag, FRAGMENT_PROMOCOES)
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
                executaFragmentPromocoes()
                return true
            }

            R.id.home -> {
                finish()
                return true;
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun manageFragment(fragment: Fragment, tag: String){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.flContainerForFragment as Int, fragment, tag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit()
    }

    override fun onBackPressed() {

        Log.i("TAG", "" + supportFragmentManager.fragments.size)
        val fragment = supportFragmentManager.findFragmentByTag(FRAGMENT_PROMOCOES)

        if (fragment != null) {
            if (fragment.isVisible) {
                finish()
                return
            }
        }

        super.onBackPressed()
    }
}
