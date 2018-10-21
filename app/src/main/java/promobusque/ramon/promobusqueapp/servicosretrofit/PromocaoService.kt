package promobusque.ramon.promobusqueapp.servicosretrofit

import promobusque.ramon.promobusqueapp.modelos.Promocao
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PromocaoService {

    @GET("/Api/Promocao/AddVisitaPromocao/{idPromocao}")
    fun addVisitaPromocao(@Path("idPromocao") j: Long)

    @GET("/Api/Promocao/ObterTodas")
    fun obterPromocoes(): Call<List<Promocao>>
}