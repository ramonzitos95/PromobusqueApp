package promobusque.ramon.promobusqueapp.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import org.jetbrains.annotations.NotNull
import promobusque.ramon.promobusqueapp.modelos.ConfiguracaoPromobusque
import promobusque.ramon.promobusqueapp.modelos.PromocaoFavorita
import promobusque.ramon.promobusqueapp.dao.ConfiguracaoPromobusqueDao
import promobusque.ramon.promobusqueapp.dao.PromocaoFavoritaDao


@Database(entities = [ConfiguracaoPromobusque::class, PromocaoFavorita::class], exportSchema = false, version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun configuracaoPromobusqueDao(): ConfiguracaoPromobusqueDao

    abstract fun promocaoFavoritaDao(): PromocaoFavoritaDao
}






