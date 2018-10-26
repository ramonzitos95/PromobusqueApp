package promobusque.ramon.promobusqueapp.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_configuracao.*

import promobusque.ramon.promobusqueapp.R
import promobusque.ramon.promobusqueapp.modelos.ConfiguracaoPromobusque
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences



class ConfiguracaoFragment : Fragment() {

    private var mFirebaseAuth: FirebaseAuth? = null
    private lateinit var mConfiguracoesDatabaseReference: DatabaseReference
    private var switch: Switch? = null
    val PREFS_FILENAME = "promobusque.ramon.promobusqueapp.prefs"
    val CONFIG_NOTIFICACAO = "config_notificacao"

    private val USUARIO_FIREBASE: String = mFirebaseAuth?.currentUser?.uid!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Instancia a sharedPreferences
        val prefs = this.context?.getSharedPreferences(PREFS_FILENAME, 0)
        var recebeNotification = prefs!!.getBoolean(CONFIG_NOTIFICACAO, true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mFirebaseAuth = FirebaseAuth.getInstance()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_configuracao, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.switch = view.findViewById(R.id.switch_recebenotificacao)!!

        switch!!.setOnClickListener {
            //switch?.isChecked
        }
    }
}
