package promobusque.ramon.promobusqueapp.modelos

import java.io.Serializable


class PromocaoFavorita () : Serializable {

    public var id: Long? = 0
    public var idEmpresa: Long? = 0
    public var idPromocao: Long? = 0
    public var nome: String = ""
    public var descricao: String = ""
    public var dataValidade: String = ""
    public var razaoSocialEmpresa: String = ""
    public var enderecoEmpresa: String = ""
    public var cepEmpresa: String = ""
    public var siteEmpresa: String = ""
    public var idUsuarioFirebase: String = ""

    constructor(id: Long?,
                nome: String,
                descricao: String,
                dataValidade: String,
                idEmpresa: Long?,
                idPromocao: Long?,
                razaoSocialEmpresa: String,
                enderecoEmpresa: String,
                cepEmpresa: String,
                siteEmpresa: String,
                idUsuarioFirebase: String) : this()
}