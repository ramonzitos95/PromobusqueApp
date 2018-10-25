package promobusque.ramon.promobusqueapp.fragments


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

class ConfiguracaoFragment : Fragment() {

    private var mFirebaseDatabase: FirebaseDatabase? = null
    private var mFirebaseAuth: FirebaseAuth? = null
    private lateinit var mConfiguracoesDatabaseReference: DatabaseReference
    private var switch: Switch? = null

    private val USUARIO_FIREBASE: String = mFirebaseAuth?.currentUser?.uid!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_configuracao, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.switch = view.findViewById(R.id.switch_recebenotificacao)!!

        mFirebaseDatabase = FirebaseDatabase.getInstance()
        mFirebaseAuth = FirebaseAuth.getInstance()
        mConfiguracoesDatabaseReference = mFirebaseDatabase?.getReference("Configuracoes")!!

        switch!!.setOnClickListener {
            val config = ConfiguracaoPromobusque(recebeNotificacao = false, idUsuarioFirebase = USUARIO_FIREBASE)
            mConfiguracoesDatabaseReference.push().setValue(config)
            //switch?.isChecked
        }
    }
}
