package promobusque.ramon.promobusqueapp.modelos

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity
class PromocaoFavorita (
    @PrimaryKey(autoGenerate = true)
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