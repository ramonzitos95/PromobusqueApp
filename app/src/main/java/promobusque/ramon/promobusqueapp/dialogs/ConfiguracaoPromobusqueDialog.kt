package promobusque.ramon.promobusqueapp.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.form_configuracao_promobusque.view.*
import promobusque.ramon.promobusqueapp.R
import promobusque.ramon.promobusqueapp.db.DatabasePromo
import promobusque.ramon.promobusqueapp.modelos.ConfiguracaoPromobusque


class ConfiguracaoPromobusqueDialog (
    val context: Context? = null,
    val viewGroup: ViewGroup? = null
){

    private val dao = DatabasePromo().getInstance(context!!)!!.configuracaoPromobusqueDao()
    private val viewCriada = criaLayout()


    fun abre() {
            AlertDialog.Builder(context).setTitle("Configurações").setView(this.viewCriada)
                .setPositiveButton("Salvar"
                ) { _, i ->
                    salvaConfiguracaoNoBanco(this.viewCriada.switch_recebenotificacao.isChecked)
                }
            .setNegativeButton("Cancelar", null).show()
    }

    private fun setaSwitch() {
        val config = this.dao.findById(1)
        if (config != null) {
            this.viewCriada.switch_recebenotificacao.isChecked = config.RecebeNotificacao
        }
    }

    private fun salvaConfiguracaoNoBanco(recebeNotificacao: Boolean) {

        val configuracaoExistente = this.dao.findById(1)
        if (configuracaoExistente == null) {
            this.dao.add(ConfiguracaoPromobusque(0, recebeNotificacao))
            return
        }
        this.dao.update(configuracaoExistente)
    }

    private fun criaLayout(): View {
        val inflate = LayoutInflater.from(this.context)
            .inflate(R.layout.form_configuracao_promobusque, this.viewGroup, false)
        return inflate
    }
}