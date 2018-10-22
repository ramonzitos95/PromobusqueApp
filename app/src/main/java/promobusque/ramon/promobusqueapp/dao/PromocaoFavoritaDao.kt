package promobusque.ramon.promobusqueapp.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import promobusque.ramon.promobusqueapp.modelos.ConfiguracaoPromobusque
import promobusque.ramon.promobusqueapp.modelos.PromocaoFavorita

@Dao
interface PromocaoFavoritaDao {

    @Query("SELECT * FROM PromocaoFavorita")
    fun all(): List<PromocaoFavorita>

    @Insert
    fun add(vararg promocaoFavorita: PromocaoFavorita)

    @Update
    fun update(vararg promocaoFavorita: PromocaoFavorita)

    @Query("SELECT * FROM PromocaoFavorita WHERE IdPromocao = :idPromocao")
    fun findByIdPromocao(idPromocao: Long): PromocaoFavorita
}