package promobusque.ramon.promobusqueapp.modelos

import java.io.Serializable

class Promocao () : Serializable
{
    val Id: Long = 0
    val Nome: String = ""
    val Descricao: String = ""
    val Situacao: Int = 0
    val DataValidade: String = ""
    val IdEmpresa: Long? = null
    val IdCategoria: Long? = null
    val Empresa: Empresa? = null

    constructor(Id: Long,
                Nome: String,
                Descricao: String,
                Situacao: Int,
                DataValidade: String,
                IdEmpresa: Long?,
                IdCategoria: Long?,
                Empresa: Empresa?) : this()
}