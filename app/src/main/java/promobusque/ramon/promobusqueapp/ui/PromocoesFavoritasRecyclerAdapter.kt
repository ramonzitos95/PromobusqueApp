package promobusque.ramon.promobusqueapp.ui

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_promocao_recyclerview.view.*
import promobusque.ramon.promobusqueapp.DetalhesPromocaoActivity
import promobusque.ramon.promobusqueapp.R
import promobusque.ramon.promobusqueapp.extension.inflate
import promobusque.ramon.promobusqueapp.modelos.Empresa
import promobusque.ramon.promobusqueapp.modelos.Promocao
import promobusque.ramon.promobusqueapp.modelos.PromocaoFavorita

class PromocoesFavoritasRecyclerAdapter(private val favoritas: List<PromocaoFavorita>,
                                        private val contexto: Context)
    : RecyclerView.Adapter<PromocoesFavoritasRecyclerAdapter.FavoritaHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PromocoesFavoritasRecyclerAdapter.FavoritaHolder {
        val inflatedView = parent.inflate(R.layout.item_promocaofavorita_recyclerview, false)
        return FavoritaHolder(inflatedView)
    }

    override fun getItemCount(): Int {
      return favoritas.size
    }

    override fun onBindViewHolder(holder: PromocoesFavoritasRecyclerAdapter.FavoritaHolder, position: Int) {
        val itemfavorita = favoritas[position]
        holder.bindPromocaoFavotira(itemfavorita, contexto)
    }

    //1
    class FavoritaHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        //2
        private var view: View = v
        private var promocao: PromocaoFavorita? = null
        private var context: Context? = null

        //3
        init {
            v.setOnClickListener(this)
        }

        //4
        override fun onClick(v: View) {
            Log.d("RecyclerView", "CLICK!")

            var empresa = Empresa(Nome = promocao?.razaoSocialEmpresa, Endereco = promocao?.enderecoEmpresa, Site = promocao?.siteEmpresa, Cep = promocao?.siteEmpresa)!!
            val promo = Promocao(
                0,
                promocao?.nome!!,
                promocao?.descricao!!,
                1,
                promocao?.dataValidade!!,
                promocao?.idEmpresa,
                0,
                empresa
            )

            val intent = Intent(this.context, DetalhesPromocaoActivity::class.java)

            intent.putExtra("promocao", promo)

            context?.startActivity(intent)

        }

        companion object {
            //5
            private val PROMOCAO_FAVORITA_KEY = "PROMOCAO_FAVORITA"
        }

        fun bindPromocaoFavotira(promocao: PromocaoFavorita, contexto: Context) {
            this.promocao = promocao
            this.context = contexto

            with(view){
                textView_datavalidade.text = promocao.dataValidade
                textView_descricao.text = promocao.descricao
                textView_nomepromocao.text = promocao.nome
                textView_razaosocial.text = promocao?.razaoSocialEmpresa
                textView_cep.text = promocao?.cepEmpresa
            }
        }
    }
}

