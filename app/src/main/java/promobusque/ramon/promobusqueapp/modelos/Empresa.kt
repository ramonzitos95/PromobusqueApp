package promobusque.ramon.promobusqueapp.modelos

import java.io.Serializable

class Empresa(

) : Serializable
{
    val IdEmpresa: Long = 0
    val RazaoSocial: String = ""
    val Cnpj: String = ""
    val Cep: String = ""
    val Site: String = ""
    val Telefone: String = ""
    val Celular: String = ""
    val Endereco: String = ""
    val Estado: String = ""
    val Cidade: String = ""

    constructor(Nome: String?, Endereco: String?, Site: String?, Cep: String?): this()
}