package promobusque.ramon.promobusqueapp.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import promobusque.ramon.promobusqueapp.dao.ConfiguracaoPromobusqueDao
import promobusque.ramon.promobusqueapp.dao.PromocaoFavoritaDao
import promobusque.ramon.promobusqueapp.modelos.ConfiguracaoPromobusque
import promobusque.ramon.promobusqueapp.modelos.PromocaoFavorita

@Database(entities = [ConfiguracaoPromobusque::class, PromocaoFavorita::class], version = 2)
abstract class DatabasePromo : RoomDatabase() {

    abstract fun configuracaoPromobusqueDao(): ConfiguracaoPromobusqueDao
    abstract fun promocaoFavoritaDao(): PromocaoFavoritaDao

    private val DB_NAME = "promobusque.db"
    private lateinit var INSTANCE: DatabasePromo

    public fun getAppDatabase(context: Context): DatabasePromo {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.applicationContext, DatabasePromo::class.java, DB_NAME)
                .allowMainThreadQueries().build()

            //Room.inMemoryDatabaseBuilder(context.getApplicationContext(),AppDatabase.class).allowMainThreadQueries().build();
        }
        return INSTANCE
    }

}