package promobusque.ramon.promobusqueapp.dialogs

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.layout_compartilhar_whats.view.*
import promobusque.ramon.promobusqueapp.R


class DialogEnviarPromocaoWhats(var textoCompartilhamento: String,
                                val contexto: Context,
                                val viewGroup: ViewGroup)
{

    private val viewCriada = criaLayout()

    fun CriaDialogo() {
        AlertDialog.Builder(contexto)
            .setTitle("Compartilhamento de promoção")
            .setView(viewCriada)
            .setPositiveButton("Enviar"
            ) { _, i ->

                enviarWhatsApp()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    fun enviarWhatsApp() {
        val pm = this.contexto.packageManager
        try {

            val waIntent = Intent(Intent.ACTION_SEND)
            waIntent.type = "text/plain"

            val info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA)
            waIntent.setPackage("com.whatsapp")

            waIntent.putExtra(Intent.EXTRA_TEXT, textoCompartilhamento)
            contexto.startActivity(waIntent)

        } catch (e: PackageManager.NameNotFoundException) {
            Toast.makeText(contexto, "WhatsApp não instalado", Toast.LENGTH_SHORT).show()
        }

    }

    private fun criaLayout(): View {
        val view = LayoutInflater.from(contexto)
            .inflate(R.layout.layout_compartilhar_whats,
                viewGroup,
                false)

        view.edtMensagem.setText(this.textoCompartilhamento)
        view.edtMensagem.isEnabled = false

        return view
    }
}