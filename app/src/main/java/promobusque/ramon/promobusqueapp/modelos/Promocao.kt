package promobusque.ramon.promobusqueapp.modelos

import java.io.Serializable

class Promocao () : Serializable
{
    var Id: Long = 0
    var Nome: String = ""
    var Descricao: String = ""
    var Situacao: Int = 0
    var DataValidade: String = ""
    var IdEmpresa: Long? = 0
    var IdCategoria: Long? = 0
    var Empresa: Empresa? = null

    constructor(Id: Long,
                Nome: String,
                Descricao: String,
                Situacao: Int,
                DataValidade: String,
                IdEmpresa: Long?,
                IdCategoria: Long?,
                Empresa: Empresa?) : this() {

        this.Id = Id
        this.Nome = Nome
        this.Descricao = Descricao
        this.Situacao = Situacao
        this.DataValidade = DataValidade
        this.IdEmpresa = IdEmpresa
        this.IdCategoria = IdCategoria
        this.Empresa = Empresa
    }
}