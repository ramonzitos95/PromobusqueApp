package promobusque.ramon.promobusqueapp.modelos

import java.io.Serializable


class PromocaoFavorita (
    val Id: Long,
    val Nome: String,
    val Descricao: String,
    val DataValidade: String,
    val IdEmpresa: Long,
    val IdPromocao: Long,
    val RazaoSocialEmpresa: String,
    val EnderecoEmpresa: String,
    val CepEmpresa: String,
    val SiteEmpresa: String
) : Serializable