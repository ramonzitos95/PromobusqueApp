package promobusque.ramon.promobusqueapp.dao

import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import promobusque.ramon.promobusqueapp.modelos.ConfiguracaoPromobusque
import android.arch.persistence.room.Update
import org.jetbrains.annotations.NotNull


interface ConfiguracaoPromobusqueDao {

    @Insert
    fun add(vararg configuracaoPromobusqueArr: ConfiguracaoPromobusque)

    @Query("SELECT * FROM ConfiguracaoPromobusque WHERE Id = :id")
    fun findById(id: Long): ConfiguracaoPromobusque

    @Query("SELECT * FROM ConfiguracaoPromobusque")
    fun get(): List<ConfiguracaoPromobusque>

    @Update
    fun update(vararg configuracaoPromobusqueArr: ConfiguracaoPromobusque)
}