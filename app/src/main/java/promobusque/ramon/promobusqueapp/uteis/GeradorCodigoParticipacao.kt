package promobusque.ramon.promobusqueapp.uteis

class GeradorCodigoParticipacao {

    private val novoCodigo: Long
        get() = (100000 + Math.random() * 899999L).toLong()

    val novoCodigoString: String
        get() = novoCodigo.toString()
}