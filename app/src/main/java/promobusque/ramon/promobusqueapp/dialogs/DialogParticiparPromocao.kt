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
import com.squareup.moshi.Moshi
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
    private lateinit var participacao: Participacao

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

            //Prepara objeto de participação para gerar o Json
            val participacao = Participacao(
                CodigoGerado = codigoParticipacao,
                IdUsuarioFirebase = idUsuarioFirebase,
                IdEmpresa = promocao?.IdEmpresa ?: 0,
                IdPromocao = promocao.Id)

            val json = geraJsonParticipacao(participacao)

            try {
                var helperQrCode = HelperQrCode(this.contexto, this.resources)

                val bitmap = helperQrCode.TextToImageEncode(json)

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

    //Envia participação para api
    private fun enviarParticipacaoParaApi() {

        val call = RetrofitInitializer().participacaoService()
            .Gravar(participacao)
            .enqueue(object: Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.isSuccessful)
                    Log.i(promobusque.ramon.promobusqueapp.modelos.TAG, "participação incluida com sucesso ${promocao.IdEmpresa} - ${promocao.Empresa?.RazaoSocial}, mensagem: ${response.message()}")
                else
                    Log.i(promobusque.ramon.promobusqueapp.modelos.TAG, "Ocorreu um erro ao incluir participação ${response.message()}")
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e(promobusque.ramon.promobusqueapp.modelos.TAG, "ocorreu um erro ao incluir participação de promoção: ${t.message}")
            }
        })
    }

    //Gera json participacação com Moshi
    private fun geraJsonParticipacao(participacao: Participacao) : String {

        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter<Participacao>(Participacao::class.java)
        val json = adapter.toJson(participacao)

        return json
    }

}