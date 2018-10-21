package promobusque.ramon.promobusqueapp.servicosretrofit

import retrofit2.http.GET
import retrofit2.http.Path

interface EmpresaService {

    @GET("/Api/Empresa/AddQuantidadeFavorito/{idEmpresa}")
    fun AddQuantidadeFavorito(@Path("idEmpresa") idEmpresa: Long)
}