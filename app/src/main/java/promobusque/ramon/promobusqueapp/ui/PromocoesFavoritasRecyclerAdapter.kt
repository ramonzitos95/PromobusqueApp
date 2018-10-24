package promobusque.ramon.promobusqueapp.ui

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_promocao_recyclerview.view.*
import promobusque.ramon.promobusqueapp.R
import promobusque.ramon.promobusqueapp.extension.inflate
import promobusque.ramon.promobusqueapp.modelos.PromocaoFavorita

class PromocoesFavoritasRecyclerAdapter(private val favoritas: List<PromocaoFavorita>)
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
        holder.bindPromocaoFavotira(itemfavorita)
    }

    //1
    class FavoritaHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        //2
        private var view: View = v
        private var promocao: PromocaoFavorita? = null

        //3
        init {
            v.setOnClickListener(this)
        }

        //4
        override fun onClick(v: View) {
            Log.d("RecyclerView", "CLICK!")
        }

        companion object {
            //5
            private val PROMOCAO_FAVORITA_KEY = "PROMOCAO_FAVORITA"
        }

        fun bindPromocaoFavotira(promocao: PromocaoFavorita) {
            this.promocao = promocao

            with(view){
                textView_datavalidade.text = promocao.DataValidade
                textView_descricao.text = promocao.Descricao
                textView_nomepromocao.text = promocao.Nome
                textView_razaosocial.text = promocao?.RazaoSocialEmpresa
                textView_cep.text = promocao?.CepEmpresa
            }
        }
    }
}

