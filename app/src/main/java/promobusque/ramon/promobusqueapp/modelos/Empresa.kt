package promobusque.ramon.promobusqueapp.modelos

import java.io.Serializable

class Empresa(
    val IdEmpresa: String,
    val RazaoSocial: String,
    val Cnpj: String,
    val Cep: String,
    val Site: String,
    val Telefone: String,
    val Celular: String,
    val Endereco: String,
    val Estado: String,
    val Cidade: String
) : Serializable