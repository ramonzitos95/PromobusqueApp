package promobusque.ramon.promobusqueapp.preferences

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class AppPreferencesPrincipal : Application() {

    override fun onCreate() {
        super.onCreate()
        AppPreferences.init(this)
    }
}

object AppPreferences {
    private const val NAME = "ConfiguracoesPromobusque"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    // list of app specific preferences
    private val CONFIG_RECEBE_NOTIFICACAO = Pair("config_recebe_notificacoes", false)

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    /**
     * SharedPreferences extension function, so we won't need to call edit() and apply()
     * ourselves on every SharedPreferences operation.
     */
    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var recebeNotificacao: Boolean
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getBoolean(CONFIG_RECEBE_NOTIFICACAO.first, CONFIG_RECEBE_NOTIFICACAO.second)

        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putBoolean(CONFIG_RECEBE_NOTIFICACAO.first, value)
        }
}