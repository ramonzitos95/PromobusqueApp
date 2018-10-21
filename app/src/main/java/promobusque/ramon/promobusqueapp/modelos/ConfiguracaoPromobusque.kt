package promobusque.ramon.promobusqueapp.modelos

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity
class ConfiguracaoPromobusque (
    @PrimaryKey(autoGenerate = true)
    val Id: Int,
    val RecebeNotificacao: Boolean
) : Serializable