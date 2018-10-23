package promobusque.ramon.promobusqueapp.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_promocao_favorita.*

import promobusque.ramon.promobusqueapp.R
import promobusque.ramon.promobusqueapp.db.DatabasePromo
import promobusque.ramon.promobusqueapp.ui.PromocoesFavoritasAdapter

class PromocaoFavoritaFragment : Fragment() {

    private var contexto: Context? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        preencherListViewPromocoesFavoritas()

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_promocao_favorita, container, false)
    }

    private fun preencherListViewPromocoesFavoritas() {
        val promocaoFavoritaDao = DatabasePromo().getInstance(contexto!!)?.promocaoFavoritaDao
        val promocoesFavoritas = promocaoFavoritaDao?.all()
        list_view_promocoes_favoritas.adapter = PromocoesFavoritasAdapter(promocoesFavoritas!!, contexto!!)
    }

    override fun onAttach(context: Context) {
        contexto = context
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

}
