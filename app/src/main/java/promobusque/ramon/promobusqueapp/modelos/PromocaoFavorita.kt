package promobusque.ramon.promobusqueapp.modelos

import java.io.Serializable


class PromocaoFavorita () : Serializable {


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
                        idUsuarioFirebase: String) : this() {
        this.id = id
        this.nome = nome
        this.descricao = descricao
        this.siteEmpresa = siteEmpresa
        this.dataValidade = dataValidade
        this.idPromocao = idPromocao
        this.razaoSocialEmpresa = razaoSocialEmpresa
        this.idUsuarioFirebase = idUsuarioFirebase
        this.enderecoEmpresa = enderecoEmpresa
        this.idEmpresa = idEmpresa
        this.cepEmpresa = cepEmpresa
    }

    var id: Long? = null
    var nome: String = ""
    var descricao: String = ""
    var dataValidade: String = ""
    var idEmpresa: Long? = null
    var idPromocao: Long? = null
    var razaoSocialEmpresa: String = ""
    var enderecoEmpresa: String = ""
    var cepEmpresa: String = ""
    var siteEmpresa: String = ""
    var idUsuarioFirebase: String = ""
}