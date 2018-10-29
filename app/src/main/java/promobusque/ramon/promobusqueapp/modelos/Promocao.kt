package promobusque.ramon.promobusqueapp.modelos

import java.io.Serializable

class Promocao () : Serializable
{
    val Id: Long = 0
    val Nome: String = ""
    val Descricao: String = ""
    val Situacao: Int = 0
    val DataValidade: String = ""
    val IdEmpresa: Long? = 0
    val IdCategoria: Long? = 0
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