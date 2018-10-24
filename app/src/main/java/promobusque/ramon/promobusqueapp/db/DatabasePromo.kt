package promobusque.ramon.promobusqueapp.db
import android.content.Context
import promobusque.ramon.promobusqueapp.modelos.ConfiguracaoPromobusque
import promobusque.ramon.promobusqueapp.modelos.PromocaoFavorita

//@Database(entities = arrayOf(ConfiguracaoPromobusque::class, PromocaoFavorita::class), exportSchema = false, version = 1)
//abstract class DatabasePromo : RoomDatabase() {
//
//    abstract fun configuracaoPromobusqueDao(): ConfiguracaoPromobusqueDao
//    abstract fun promocaoFavoritaDao(): PromocaoFavoritaDao
//}
//
//object DatabaseProvider {
//    val DB_NAME : String = "promobusque"
//    private var INSTANCE : DatabasePromo? = null
//
//    fun getDataBase(context: Context): DatabasePromo? {
//        if (INSTANCE == null) {
//
//            synchronized(DatabasePromo::class.java) {
//                if (INSTANCE == null) {
//                    INSTANCE = Room.databaseBuilder(context.applicationContext, DatabasePromo::class.java, DB_NAME)
//                        .fallbackToDestructiveMigration().build()
//                }
//            }
//        }
//
//        return INSTANCE
//    }
//}