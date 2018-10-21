package promobusque.ramon.promobusqueapp.servicosretrofit

import retrofit2.http.GET
import retrofit2.http.Path

interface EmpresaService {
    @GET("/Empresa/AddQuantidadeFavorito/{idEmpresa}")
    fun list(@Path("idEmpresa") idEmpresa: Long)
}