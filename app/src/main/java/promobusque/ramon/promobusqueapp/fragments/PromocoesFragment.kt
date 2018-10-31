package promobusque.ramon.promobusqueapp.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.MultiAutoCompleteTextView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_promocoes.*
import promobusque.ramon.promobusqueapp.R
import promobusque.ramon.promobusqueapp.modelos.Promocao
import promobusque.ramon.promobusqueapp.modelos.PromocaoFavorita
import promobusque.ramon.promobusqueapp.modelos.TAG
import promobusque.ramon.promobusqueapp.retrofit.RetrofitInitializer
import promobusque.ramon.promobusqueapp.ui.PromocoesAdapter
import promobusque.ramon.promobusqueapp.ui.PromocoesFavoritasRecyclerAdapter
import promobusque.ramon.promobusqueapp.ui.PromocoesRecyclerAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PromocoesFragment : Fragment() {

    var contexto: Context? = null
    private var mFirebaseDatabase: FirebaseDatabase? = null
    private lateinit var mPromocoesDatabaseReference: DatabaseReference

    //Duas listas vinda de locais diferentes
    private var promocoesApi: MutableList<Promocao>? = mutableListOf()
    private var promocoesFirebase: MutableList<Promocao>? = mutableListOf()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var recyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_promocoes, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mFirebaseDatabase = FirebaseDatabase.getInstance()
        mPromocoesDatabaseReference = mFirebaseDatabase!!.reference.child("promocoes")

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configuraRecyclerView()

        //Busca todas as promoções e preenche o adapter
        this.consultaPromocoesApi()
    }

    private fun consultaPromocoesApi() {
        val call = RetrofitInitializer().promocaoService().obterPromocoes()

        call.enqueue(object : Callback<List<Promocao>> {
            override fun onResponse(call: Call<List<Promocao>>?, response: Response<List<Promocao>>?) {

                if (response != null && response.isSuccessful) {
                    response?.let {
                        promocoesApi = it.body() as MutableList<Promocao>
                        salvaPromocoesFirebase(promocoesApi)
                        configuraAdapter(promocoesApi!!)
                    }
                } else {
                    Log.e("Promobusque", "ocorreu um problema na requisição: " + (response?.errorBody() ?: ""))
                    consultaPromocoesFirebase()
                }
            }

            override fun onFailure(call: Call<List<Promocao>>?, t: Throwable?) {
                Log.e("Promobusque", "ocorreu um problema na requisição: " + (t?.message ?: ""))
                consultaPromocoesFirebase()
            }
        })
    }

    //Salva as promoções no firebase, primeiramente as excluindo e depois inserindo
    private fun salvaPromocoesFirebase(promocoesApi: List<Promocao>?) {
        if(promocoesApi?.size!! > 0)
            excluiPromocoes()

        for(promo in promocoesApi)
        {
            mPromocoesDatabaseReference.push().setValue(promo)
        }

    }

    private fun consultaPromocoesFirebase()
    {
        val reference = mPromocoesDatabaseReference.orderByChild("dataValidade")

        reference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(erroDatabase: DatabaseError) {
                val message = erroDatabase.message
                Log.d(TAG,"Erro no firebase db: $message ")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d(TAG,"Snapshot promoção: $snapshot")

                for (promocao in snapshot.children)
                {
                    var promocao = promocao.getValue(Promocao::class.java)

                    if(promocao != null)
                        promocoesFirebase?.add(promocao)

                }

                configuraAdapter(promocoesFirebase!!)
            }

        })
    }

    private fun excluiPromocoes() {
        mPromocoesDatabaseReference.removeValue()
    }

    private fun configuraAdapter(promocoes: MutableList<Promocao>) {
        atualizaAdapter(promocoes!!)
    }

    fun atualizaAdapter(promocoes: List<Promocao>) {
        if(promocoes.isNotEmpty())
            recyclerView?.adapter = PromocoesRecyclerAdapter(promocoes, this.contexto!!)

    }

    private fun configuraRecyclerView()
    {
        recyclerView = view?.findViewById<RecyclerView>(R.id.recycler_view_promocoes)!!

        //Recycler View
        linearLayoutManager = LinearLayoutManager(activity)
        recyclerView!!.layoutManager = linearLayoutManager
    }

    override fun onAttach(context: Context) {
        contexto = context
        super.onAttach(context)
    }

}
