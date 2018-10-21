package promobusque.ramon.promobusqueapp.db

import android.arch.persistence.room.Room
import android.content.Context

class DatabasePromo {

    private val DB_NAME = "promobusque.db"
    private var instance: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase? {
        if (instance == null) {
            instance = create(context)
        }
        return instance
    }

    private fun create(context: Context): AppDatabase? {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java!!,
            DB_NAME
        )
            //.allowMainThreadQueries() //Permite rodar na thread principal
            //.fallbackToDestructiveMigration() //Ao realizar a migração descomentar este trecho
            .build()
    }
}