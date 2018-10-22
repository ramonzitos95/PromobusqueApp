package promobusque.ramon.promobusqueapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_detalhes_promocao.*
import kotlinx.android.synthetic.main.content_detalhes_promocao.*
import promobusque.ramon.promobusqueapp.modelos.Promocao


class DetalhesPromocaoActivity : AppCompatActivity() {

    private lateinit var promocao: Promocao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_promocao)
        setSupportActionBar(toolbar)

        preencherCampos()
        setListenerBotaoMapa();
        setListenerBotaoCompartilhar();
        setListenerBotaoSite();
    }

    private fun setListenerBotaoSite() {
        button_visitar_site.setOnClickListener {
            abrirSiteEstabelecimento()
        }
    }

    private fun setListenerBotaoCompartilhar() {
        button_compartilhar.setOnClickListener {
            compartilharPromocao()
        }
    }

    //Abre site do estabelecimento
    fun abrirSiteEstabelecimento(){
        if(promocao.Empresa != null && !(promocao.Empresa?.Site.isNullOrEmpty()))
        {
            val i = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(promocao.Empresa?.Site)
            )
            startActivity(i)
        } else {
            Toast.makeText(this, "Este estabelecimento não possue site!", Toast.LENGTH_SHORT ).show()
        }
    }

    fun compartilharPromocao() {
        val textoCompartilhamento: StringBuilder = StringBuilder()
        textoCompartilhamento.appendln("Promoção: " + promocao.Nome)
        textoCompartilhamento.appendln("Descrição: " + promocao.Descricao)
        textoCompartilhamento.append("Local: " + promocao.Empresa?.Endereco)

        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_STREAM,textoCompartilhamento.toString())
        startActivity(Intent.createChooser(intent, "Compartilhar promoção"))
    }

    private fun setListenerBotaoMapa() {
        button_mapa.setOnClickListener {
            abrirGoogleMaps()
        }
    }

    fun abrirGoogleMaps(){
        if(promocao.Empresa != null && promocao.Empresa!!.Endereco.isNotEmpty())
        {
            val intent = Intent(this, GoogleMapsActivity::class.java)
            intent.putExtra("empresa", promocao?.Empresa)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Não será possível abrir o mapa, pois o estabelecimento não possui endereço", Toast.LENGTH_SHORT).show()
        }
    }

    fun preencherCampos() {
        promocao = getIntent().getSerializableExtra("promocao") as Promocao

        with(promocao)
        {
            text_datavalidade.text = "Data de validade: $DataValidade"
            text_descricao.text = "Descrição: $Descricao"
            text_nomepromocao.text = "Promoção: $Nome"

            if(Empresa != null)
            {
                with(Empresa)
                {

                    if(!(this?.Endereco?.isEmpty()!!))
                        text_endereco.text = "Endereço: $Endereco"

                    if(!(Cep.isEmpty()))
                        text_cep.text = "Cep: $Cep"
                }
            }
        }
    }
}
