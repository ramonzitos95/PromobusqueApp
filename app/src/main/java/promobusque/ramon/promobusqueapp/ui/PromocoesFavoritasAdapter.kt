package promobusque.ramon.promobusqueapp.ui

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import promobusque.ramon.promobusqueapp.modelos.Promocao

class PromocoesFavoritasAdapter(
    private val promocoes: List<Promocao>,
    private val context: Context
) : BaseAdapter() {
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
}