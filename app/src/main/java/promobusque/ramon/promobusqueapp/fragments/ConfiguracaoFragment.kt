package promobusque.ramon.promobusqueapp.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import promobusque.ramon.promobusqueapp.R
import promobusque.ramon.promobusqueapp.preferences.AppPreferences


class ConfiguracaoFragment : Fragment() {

    private var switch: Switch? = null
    private lateinit var contexto: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Instancia a sharedPreferences
        AppPreferences.init(context = this.contexto)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_configuracao, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.switch = view?.findViewById(R.id.switch_recebenotificacao)
        this.switch?.setChecked(AppPreferences.recebeNotificacao)

        setaClickSwitch()
    }

    fun setaClickSwitch(){
        switch!!.setOnClickListener {
            AppPreferences.recebeNotificacao = switch?.isChecked!!
            Log.d("Configuracao", "The value of our pref is: ${AppPreferences.recebeNotificacao}")
        }
    }

    override fun onAttach(context: Context) {
        contexto = context
        super.onAttach(context)
    }
}
