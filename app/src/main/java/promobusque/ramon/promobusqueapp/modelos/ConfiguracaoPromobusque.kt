package promobusque.ramon.promobusqueapp.modelos

import java.io.Serializable

class ConfiguracaoPromobusque (

) : Serializable {

    val recebeNotificacao: Boolean = false
    val idUsuarioFirebase: String = ""

    constructor(recebeNotificacao: Boolean,
                idUsuarioFirebase: String) : this()
}