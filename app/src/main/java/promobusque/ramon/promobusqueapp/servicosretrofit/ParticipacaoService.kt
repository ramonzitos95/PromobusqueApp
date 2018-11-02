package promobusque.ramon.promobusqueapp.servicosretrofit

import promobusque.ramon.promobusqueapp.modelos.Participacao
import promobusque.ramon.promobusqueapp.modelos.Promocao
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ParticipacaoService {

    @POST("/Api/Participacao/Gravar")
    fun Gravar(@Body participacao: Participacao) : Call<Void>

    @GET("/Api/Promocao/ObterTodas")
    fun obterParticipacao(): Call<List<Promocao>>
}