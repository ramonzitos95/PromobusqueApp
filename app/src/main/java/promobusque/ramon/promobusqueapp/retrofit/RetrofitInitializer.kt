package promobusque.ramon.promobusqueapp.retrofit

import promobusque.ramon.promobusqueapp.servicosretrofit.EmpresaService
import promobusque.ramon.promobusqueapp.servicosretrofit.PromocaoService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {

    val url = "http://localhost:5000"

    private val retrofit = Retrofit.Builder()
                                    .baseUrl(url)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build()

    fun empresaService(): EmpresaService = retrofit.create(EmpresaService::class.java)
    fun promocaoService(): PromocaoService = retrofit.create(PromocaoService::class.java)
}