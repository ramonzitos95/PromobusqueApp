package promobusque.ramon.promobusqueapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_detalhes_promocao.*
import kotlinx.android.synthetic.main.content_detalhes_promocao.*
import promobusque.ramon.promobusqueapp.dialogs.DialogEnviarPromocaoWhats
import promobusque.ramon.promobusqueapp.dialogs.DialogParticiparPromocao
import promobusque.ramon.promobusqueapp.modelos.Promocao
import promobusque.ramon.promobusqueapp.modelos.PromocaoFavorita
import promobusque.ramon.promobusqueapp.retrofit.RetrofitInitializer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetalhesPromocaoActivity : AppCompatActivity() {

    private lateinit var promocao: Promocao
    private lateinit var mFirebaseAuth: FirebaseAuth
    private var myUserId = ""
    var registroFavoritaEncontrada : Boolean = false

    private var modulo: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_promocao)
        setSupportActionBar(toolbar)

        mFirebaseAuth = FirebaseAuth.getInstance()
        myUserId = mFirebaseAuth.currentUser?.uid.toString()

        preencherCampos()

        contaVisitasPromocao()

        setListenerBotaoMapa()

        setListenerBotaoCompartilhar()

        setListenerBotaoSite()

        setListemerBotaoParticipar()

        configuraBandoDeDados()

        customizarActionBar()
    }

    private fun setListemerBotaoParticipar() {
        button_participar.setOnClickListener {
            val dialog = DialogParticiparPromocao(contexto = this, viewGroup = window.decorView as ViewGroup, promocao = promocao, resources = resources, idUsuarioFirebase = myUserId)
            dialog.CriaDialogo()
        }

    }

    fun customizarActionBar(){
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        if(promocao.Nome.isNotEmpty())
            supportActionBar?.title = "Promoção: ${promocao.Nome}"
        else
            supportActionBar?.title = "Promoção"
    }

    private fun configuraBandoDeDados() {
        mFirebaseDatabase = FirebaseDatabase.getInstance()
        mPromocoesFavoritasDatabaseReference = mFirebaseDatabase!!.getReference().child("promocoesfavoritas")
    }

    private fun contaVisitasPromocao() {
        //Adiciona um contador de visita a promoção
        RetrofitInitializer().promocaoService().addVisitaPromocao(promocao.Id)
            .enqueue(object: Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    Log.i(promobusque.ramon.promobusqueapp.modelos.TAG, "Visita adicionada a promoção ${promocao.Id} - ${promocao.Nome}")
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e(promobusque.ramon.promobusqueapp.modelos.TAG, "ocorreu um erro ao adicionar visita: ${t.message}")
                }
            })
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
        var url = promocao.Empresa?.Site.toString()
        if(promocao.Empresa != null && !url.isNullOrEmpty())
        {
            if (!url.startsWith("http://") && !url.startsWith("https://")){
                url = "http://$url";
            }

            val i = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(url)
            )
            startActivity(i)

        } else {
            Toast.makeText(this, "Este estabelecimento não possue site!", Toast.LENGTH_SHORT ).show()
        }
    }

    fun compartilharPromocao() {

        val textoCompartilhamento: StringBuilder = StringBuilder()
        textoCompartilhamento.append("Promoção: " + promocao.Nome)
        textoCompartilhamento.append(" Descrição: " + promocao.Descricao)
        textoCompartilhamento.append(" Local: " + promocao.Empresa?.Endereco)

        var dialog = DialogEnviarPromocaoWhats(
            contexto = this,
            textoCompartilhamento = textoCompartilhamento.toString(),
            viewGroup = window.decorView as ViewGroup
        )

        dialog.CriaDialogo()
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
        promocao = intent.getSerializableExtra("promocao") as Promocao

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

                    if(RazaoSocial.isNotEmpty())
                        text_razaosocial.text = "Razão social: $RazaoSocial"
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_detalhes_promocao, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.op_favorite_promocao -> {
                verificaSePromocaoFoiAdicionadaAosFavoritos()
                return true
            }

            R.id.home -> {
                finish()
                return true;
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    private lateinit var mFirebaseDatabase: FirebaseDatabase
    private lateinit var mPromocoesFavoritasDatabaseReference: DatabaseReference

    private fun verificaSePromocaoFoiAdicionadaAosFavoritos() {

        var query = mPromocoesFavoritasDatabaseReference.orderByKey()
        query.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.e("Erro Consulta promo favoritas: ", p0.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (favorita in snapshot.children) {
                    var f = favorita.getValue(PromocaoFavorita::class.java)
                    if (f?.idPromocao == promocao.Id) {
                        registroFavoritaEncontrada = true
                        break
                    }
                }

                adicionarPromocaoAosFavoritos()
            }

        })
    }

    private fun adicionarPromocaoAosFavoritos() {
        if(!registroFavoritaEncontrada)
        {
            val promocaoFavorita = PromocaoFavorita(idUsuarioFirebase = myUserId.toString(),
                nome = promocao.Nome,
                descricao = promocao.Descricao,
                dataValidade = promocao.DataValidade,
                idPromocao = promocao.Id,
                idEmpresa = promocao.IdEmpresa ?: 0,
                cepEmpresa = promocao?.Empresa?.Cep ?: "",
                enderecoEmpresa = promocao?.Empresa?.Endereco ?: "",
                razaoSocialEmpresa = promocao?.Empresa?.RazaoSocial ?: "",
                siteEmpresa = promocao?.Empresa?.Site ?: "",
                id = 0)

            mPromocoesFavoritasDatabaseReference.push().setValue(promocaoFavorita)

            contaFavoritosEmpresa()
        }
        else{
            Toast.makeText(this, "Promoção já adicionada aos favoritos", Toast.LENGTH_SHORT).show()
            registroFavoritaEncontrada = false
        }
    }

    private fun contaFavoritosEmpresa() {
        //Adiciona um contador de visita a promoção
        RetrofitInitializer().empresaService().AddQuantidadeFavorito(promocao.IdEmpresa!!)
            .enqueue(object: Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    Log.i(promobusque.ramon.promobusqueapp.modelos.TAG, "favoritada adicionada a empresa ${promocao.IdEmpresa} - ${promocao.Empresa?.RazaoSocial}")
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e(promobusque.ramon.promobusqueapp.modelos.TAG, "ocorreu um erro ao adicionar favorito: ${t.message}")
                }
            })
    }

    override fun onBackPressed() {

        //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
        startActivity(Intent(this, PromocoesActivity::class.java))
        //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem

        super.onBackPressed()
    }
}
