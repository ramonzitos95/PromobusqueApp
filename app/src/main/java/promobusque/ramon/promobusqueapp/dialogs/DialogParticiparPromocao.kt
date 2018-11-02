package promobusque.ramon.promobusqueapp.dialogs

import android.content.Context
import android.content.res.Resources
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.zxing.WriterException
import kotlinx.android.synthetic.main.dialog_participacao_promocao.view.*
import promobusque.ramon.promobusqueapp.R
import promobusque.ramon.promobusqueapp.modelos.Participacao
import promobusque.ramon.promobusqueapp.modelos.Promocao
import promobusque.ramon.promobusqueapp.modelos.TAG
import promobusque.ramon.promobusqueapp.retrofit.RetrofitInitializer
import promobusque.ramon.promobusqueapp.uteis.GeradorCodigoParticipacao
import promobusque.ramon.promobusqueapp.uteis.HelperQrCode
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DialogParticiparPromocao(val contexto: Context,
                               val viewGroup: ViewGroup,
                               val promocao: Promocao,
                               private val resources: Resources,
                               val idUsuarioFirebase: String
)
{

    private val viewCriada = criaLayout()
    private var codigoParticipacao = ""

    fun CriaDialogo() {
        AlertDialog.Builder(contexto)
            .setCustomTitle(criaViewTitulo())
            .setView(viewCriada)
            .setPositiveButton(
                "Participar"
            ) { _, _ ->

                Log.d(TAG, "Código de participação gerado: $codigoParticipacao")
                enviarParticipacaoParaApi()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun criaLayout(): View {
        val view = LayoutInflater.from(contexto)
            .inflate(R.layout.dialog_participacao_promocao,
                viewGroup,
                false)

        codigoParticipacao = GeradorCodigoParticipacao().novoCodigoString

        try{
            val texto = codigoParticipacao!!.toString()

            try {
                var helperQrCode = HelperQrCode(this.contexto, this.resources)

                val bitmap = helperQrCode.TextToImageEncode(texto)

                //Seta a imagem
                view.iv_cqrcode!!.setImageBitmap(bitmap)

                //Salva a imagem
                val path = helperQrCode.saveImage(bitmap)  //give read write permission

                Toast.makeText(contexto, "QRCode foi salvo em: -> $path", Toast.LENGTH_SHORT).show()
            } catch (e: WriterException) {
                e.printStackTrace()
            }

        }catch (e: Exception){
            e.printStackTrace()
        }

        return view
    }

    private fun criaViewTitulo(): View {
        val view = LayoutInflater.from(contexto)
            .inflate(
                R.layout.titulo_participacao_promocao,
                viewGroup,
                false)

        return view
    }

    private fun enviarParticipacaoParaApi() {

        val participacao = Participacao(
            codigoGerado = codigoParticipacao,
            idUsuarioFirebase = idUsuarioFirebase,
            idEmpresa = promocao?.IdEmpresa ?: 0,
            idPromocao = promocao.Id)

        val call = RetrofitInitializer().participacaoService()
            .Gravar(participacao)
            .enqueue(object: Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.i(promobusque.ramon.promobusqueapp.modelos.TAG, "participação incluida com sucesso ${promocao.IdEmpresa} - ${promocao.Empresa?.RazaoSocial}")
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e(promobusque.ramon.promobusqueapp.modelos.TAG, "ocorreu um erro ao incluir participação de promoção: ${t.message}")
            }
        })

    }
}