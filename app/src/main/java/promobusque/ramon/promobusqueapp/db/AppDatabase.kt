package promobusque.ramon.promobusqueapp.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import promobusque.ramon.promobusqueapp.dao.ConfiguracaoPromobusqueDao
import promobusque.ramon.promobusqueapp.dao.PromocaoFavoritaDao
import promobusque.ramon.promobusqueapp.modelos.ConfiguracaoPromobusque
import promobusque.ramon.promobusqueapp.modelos.PromocaoFavorita


@Database(entities = [ConfiguracaoPromobusque::class, PromocaoFavorita::class], version = 2)
abstract class AppDatabase: RoomDatabase() {

    abstract val configuracaoPromobusqueDao: ConfiguracaoPromobusqueDao

    abstract val promocaoFavoritaDao: PromocaoFavoritaDao
}






