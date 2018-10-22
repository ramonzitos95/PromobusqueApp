package promobusque.ramon.promobusqueapp.modelos

import java.io.Serializable

class Promocao (
    val Id: Long,
    val Nome: String,
    val Descricao: String,
    val Situacao: Short,
    val DataValidade: String,
    val IdEmpresa: Long?,
    val IdCategoria: Long?,
    val Empresa: Empresa?
) : Serializable