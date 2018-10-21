package promobusque.ramon.promobusqueapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.item_promocao_recyclerview.view.*
import promobusque.ramon.promobusqueapp.R
import promobusque.ramon.promobusqueapp.TAG
import promobusque.ramon.promobusqueapp.modelos.Promocao


class PromocoesAdapter(
    private val promocoes: List<Promocao>,
    private val context: Context
) : BaseAdapter() {


    override fun getView(posicao: Int, view: View?, parent: ViewGroup?): View {
        val viewCriada = LayoutInflater.from(context)
            .inflate(R.layout.item_promocao_recyclerview, parent, false)

        val promocao = promocoes[posicao]

        with(viewCriada){
            textView_datavalidade.text = promocao.DataValidade
            textView_descricao.text = promocao.Descricao
            textView_nomepromocao.text = promocao.Nome
            textView_razaosocial.text = promocao.Empresa!!.RazaoSocial
            textView_cep.text = promocao.Empresa.Cep
        }

        ImplementaClickItemRecycler(viewCriada, promocao)

        return viewCriada
    }

    override fun getItem(posicao: Int): Any {
        return this.promocoes[posicao]
    }

    override fun getItemId(id: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return this.promocoes.size
    }

    fun ImplementaClickItemRecycler(viewCriada: View, promocao: Promocao) {
        viewCriada.setOnClickListener {
            Toast.makeText(context, "Item da lista clicada", Toast.LENGTH_LONG).show()
        }
    }
}