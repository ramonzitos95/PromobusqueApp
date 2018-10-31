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

class PromocoesRecyclerAdapter(private val promocoes: List<Promocao>,
                               private val contexto: Context)
    : RecyclerView.Adapter<PromocoesRecyclerAdapter.PromocaoHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PromocoesRecyclerAdapter.PromocaoHolder {
        val inflatedView = parent.inflate(R.layout.item_promocao_recyclerview, false)
        return PromocaoHolder(inflatedView)
    }

    override fun getItemCount(): Int {
      return promocoes.size
    }

    override fun onBindViewHolder(holder: PromocoesRecyclerAdapter.PromocaoHolder, position: Int) {
        val itemfavorita = promocoes[position]
        holder.bindPromocaoFavotira(itemfavorita, contexto)
    }

    //1
    class PromocaoHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        //2
        private var view: View = v
        private lateinit var promocao: Promocao
        private var context: Context? = null

        //3
        init {
            v.setOnClickListener(this)
        }

        //4
        override fun onClick(v: View) {

            val intent = Intent(this.context, DetalhesPromocaoActivity::class.java)

            intent.putExtra("promocao", promocao)

            context?.startActivity(intent)
        }

        companion object {
            //5
            private val PROMOCAO_KEY = "PROMOCAO"
        }

        fun bindPromocaoFavotira(promocao: Promocao, contexto: Context) {
            this.promocao = promocao
            this.context = contexto

            with(view){
                textView_datavalidade.text = promocao.DataValidade
                textView_descricao.text = promocao.Descricao
                textView_nomepromocao.text = promocao.Nome
                textView_razaosocial.text = promocao?.Empresa?.RazaoSocial
                textView_cep.text = promocao?.Empresa?.Cep
            }

        }
    }
}

