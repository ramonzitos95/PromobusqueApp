package promobusque.ramon.promobusqueapp.retrofit

import promobusque.ramon.promobusqueapp.servicosretrofit.EmpresaService
import promobusque.ramon.promobusqueapp.servicosretrofit.ParticipacaoService
import promobusque.ramon.promobusqueapp.servicosretrofit.PromocaoService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitInitializer {

    private val url = "http://192.168.1.6:8000"
    private val retrofit = Retrofit.Builder()
                                    .baseUrl(url)
                                    .addConverterFactory(MoshiConverterFactory.create())
                                    .build()

    fun empresaService(): EmpresaService = retrofit.create(EmpresaService::class.java)
    fun promocaoService(): PromocaoService = retrofit.create(PromocaoService::class.java)
    fun participacaoService(): ParticipacaoService = retrofit.create(ParticipacaoService::class.java)
}