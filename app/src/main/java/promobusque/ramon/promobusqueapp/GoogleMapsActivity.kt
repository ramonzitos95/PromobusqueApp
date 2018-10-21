package promobusque.ramon.promobusqueapp

import android.support.v4.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_google_maps.*
import promobusque.ramon.promobusqueapp.maps.EnderecoServico
import promobusque.ramon.promobusqueapp.modelos.Empresa

class GoogleMapsActivity : OnMapReadyCallback, FragmentActivity(){

    private val mMap: GoogleMap? = null

    override fun onMapReady(p0: GoogleMap?) {
        val empresa = intent.getSerializableExtra("empresa") as Empresa

        val enderecoServico = EnderecoServico(this)
        enderecoServico.BuscaDadosPeloNome(empresa.Endereco)

        val latitude = enderecoServico.latitude
        val longitude = enderecoServico.longitude
        val endereco2 = enderecoServico.endereco

        if (map != null) {
            val locationPoint = LatLng(latitude, longitude)
            mMap!!.addMarker(MarkerOptions().position(LatLng(latitude, longitude)).title(endereco2))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationPoint, 15.5f))
        }
    }
}