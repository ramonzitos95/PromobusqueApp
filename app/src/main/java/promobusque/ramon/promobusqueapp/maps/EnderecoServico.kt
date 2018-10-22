package promobusque.ramon.promobusqueapp.maps

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.widget.MultiAutoCompleteTextView
import android.widget.Toast
import java.io.IOException


class EnderecoServico(val context: Context) {

    var latitude: Double = 0.0
    var longitude: Double = 0.0
    var bairro: String = ""
    var endereco: String = ""

    fun BuscaDadosPeloNome(localizacao: String) {
        val myLocation: List<*>? = null
        try {
            populaLocalizacao(Geocoder(this.context).getFromLocationName(localizacao, 1))
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun BuscaDadosPelaLatELong(longitude: Double, latitude: Double) {
        val myLocation: List<*>? = null
        try {
            populaLocalizacao(Geocoder(this.context).getFromLocation(latitude, longitude, 1))
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun populaLocalizacao(myLocation: List<*>?) {
        if (myLocation == null || myLocation.size <= 0) {
            Toast.makeText(this.context, "endereço não localizado", Toast.LENGTH_SHORT).show()
        } else {
            var a: Address? = myLocation[0] as Address?
            if(a != null)
            {
                latitude = a.latitude
                longitude = a.longitude
                //endereco = a!!.locality
                //bairro = a!!.subLocality
            }
        }
    }
}