package promobusque.ramon.promobusqueapp.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import promobusque.ramon.promobusqueapp.modelos.PromocaoFavorita

@Dao
interface PromocaoFavoritaDao {

    @Query("SELECT * FROM PromocaoFavorita")
    fun all(): List<PromocaoFavorita>

    fun add(vararg promocaoFavorita: PromocaoFavorita)
}