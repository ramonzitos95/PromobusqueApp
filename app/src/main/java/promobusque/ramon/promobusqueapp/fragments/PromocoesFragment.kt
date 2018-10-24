package promobusque.ramon.promobusqueapp.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_promocoes.*
import promobusque.ramon.promobusqueapp.R
import promobusque.ramon.promobusqueapp.modelos.Promocao
import promobusque.ramon.promobusqueapp.retrofit.RetrofitInitializer
import promobusque.ramon.promobusqueapp.ui.PromocoesAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PromocoesFragment : Fragment() {

    var contexto: Context? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_promocoes, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Busca todas as promoções e preenche o adapter
        this.preencheAdapter()

    }

    private fun preencheAdapter() {
        val call = RetrofitInitializer().promocaoService().obterPromocoes()

        call.enqueue(object : Callback<List<Promocao>> {
            override fun onResponse(call: Call<List<Promocao>>?, response: Response<List<Promocao>>?) {

                if (response != null && response.isSuccessful) {
                    response?.let {
                        val promocoes: List<Promocao>? = it.body()
                        atualizaAdapter(promocoes!!)
                    }
                } else {
                    Log.e("Promobusque", "ocorreu um problema na requisição: " + (response?.errorBody() ?: ""))
                }
            }

            override fun onFailure(call: Call<List<Promocao>>?, t: Throwable?) {
                Log.e("Promobusque", "ocorreu um problema na requisição: " + (t?.message ?: ""))
            }
        })
    }

    fun atualizaAdapter(promocoes: List<Promocao>) {
        list_view_promocoes.adapter = PromocoesAdapter(promocoes, this!!.contexto!!)
    }

    override fun onAttach(context: Context) {
        contexto = context
        super.onAttach(context)
    }


}
