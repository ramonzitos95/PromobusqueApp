package promobusque.ramon.promobusqueapp.dialogs

import android.content.Context
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.dialog_participacao_promocao.view.*
import kotlinx.android.synthetic.main.titulo_participacao_promocao.view.*
import promobusque.ramon.promobusqueapp.R
import promobusque.ramon.promobusqueapp.modelos.Promocao
import promobusque.ramon.promobusqueapp.modelos.TAG
import promobusque.ramon.promobusqueapp.uteis.GeradorCodigoParticipacao


class DialogParticiparPromocao(val contexto: Context,
                               val viewGroup: ViewGroup,
                               val promocao: Promocao
)
{

    private val viewCriada = criaLayout()
    private var codigoParticipacao = ""

    fun CriaDialogo() {
        AlertDialog.Builder(contexto)
            .setCustomTitle(criaViewTitulo())
            .setView(viewCriada)
            .setPositiveButton("Participar"
            ) { _, _ ->

                participarPromocao()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun participarPromocao() {
        Log.d(TAG, "Código de participação gerado: " + codigoParticipacao)
    }

    private fun criaLayout(): View {
        val view = LayoutInflater.from(contexto)
            .inflate(R.layout.dialog_participacao_promocao,
                viewGroup,
                false)

        codigoParticipacao = GeradorCodigoParticipacao().novoCodigoString
        view.text_view_codigo_participacao.text = codigoParticipacao

        return view
    }

    private fun criaViewTitulo(): View {
        val view = LayoutInflater.from(contexto)
            .inflate(
                R.layout.titulo_participacao_promocao,
                viewGroup,
                false)

        var texto = view.textView_titulo_gerado.text.toString()
        texto += " ${promocao.Empresa?.RazaoSocial} para a promoção ${promocao.Nome}"
        view.textView_titulo_gerado.text = texto

        return view
    }
}