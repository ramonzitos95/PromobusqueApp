package promobusque.ramon.promobusqueapp

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;

import kotlinx.android.synthetic.main.activity_promocoes_favoritas.*

class PromocoesFavoritasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promocoes_favoritas)
        setSupportActionBar(toolbar)
    }

}
