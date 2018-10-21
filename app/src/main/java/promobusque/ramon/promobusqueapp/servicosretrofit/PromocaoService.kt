package promobusque.ramon.promobusqueapp.servicosretrofit

import promobusque.ramon.promobusqueapp.modelos.Promocao
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PromocaoService {

    @GET("/Promocao/ObterTodas")
    fun list() : Call<List<Promocao>>

    @GET("/Promocao/AddVisitaPromocao/{idPromocao}")
    fun list(@Path("idPromocao") idPromocao: Long)
}