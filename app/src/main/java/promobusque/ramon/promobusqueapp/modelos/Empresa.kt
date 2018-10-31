package promobusque.ramon.promobusqueapp.modelos

import java.io.Serializable

class Empresa(

) : Serializable
{
    var IdEmpresa: Long = 0
    var RazaoSocial: String = ""
    var Cnpj: String = ""
    var Cep: String = ""
    var Site: String = ""
    var Telefone: String = ""
    var Celular: String = ""
    var Endereco: String = ""
    var Estado: String = ""
    var Cidade: String = ""

    constructor(Nome: String?, Endereco: String?, Site: String?, Cep: String?): this(){
        this.RazaoSocial = Nome.toString()
        this.Endereco = Endereco.toString()
        this.Site = Site.toString()
        this.Cep = Cep.toString()
    }
}