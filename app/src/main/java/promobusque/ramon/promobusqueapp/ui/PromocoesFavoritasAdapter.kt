package promobusque.ramon.promobusqueapp.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.item_promocao_recyclerview.view.*
import promobusque.ramon.promobusqueapp.DetalhesPromocaoActivity
import promobusque.ramon.promobusqueapp.R
import promobusque.ramon.promobusqueapp.modelos.Promocao
import promobusque.ramon.promobusqueapp.modelos.PromocaoFavorita

class PromocoesFavoritasAdapter(
    private val promocoes: List<PromocaoFavorita>,
    private val context: Context
) : BaseAdapter() {

    override fun getView(posicao: Int, view: View?, parent: ViewGroup?): View {
        val viewCriada = LayoutInflater.from(context)
            .inflate(R.layout.item_promocaofavorita_recyclerview, parent, false)

        val p = promocoes[posicao]

        with(viewCriada){
            textView_datavalidade.text = p.DataValidade
            textView_descricao.text = p.Descricao
            textView_nomepromocao.text = p.Nome
            textView_razaosocial.text = p?.RazaoSocialEmpresa
            textView_cep.text = p?.CepEmpresa
        }

        val promocao = Promocao(Nome = p.Nome, IdEmpresa = p.IdEmpresa, DataValidade = p.DataValidade, Descricao = p.DataValidade, IdCategoria = 0, Empresa = null, Id = 0, Situacao = 1)
        ImplementaClickItemRecycler(viewCriada, promocao)

        return viewCriada
    }

    override fun getItem(posicao: Int): Any {
        return promocoes[posicao]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return promocoes.count()
    }

    private fun ImplementaClickItemRecycler(viewCriada: View, promocao: Promocao) {
        viewCriada.setOnClickListener {
            val intent = Intent(context, DetalhesPromocaoActivity::class.java)
            intent.putExtra("promocao", promocao)
            context.startActivity(intent)
        }
    }

}