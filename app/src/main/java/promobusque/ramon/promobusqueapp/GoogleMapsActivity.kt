package promobusque.ramon.promobusqueapp

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_google_maps.*
import promobusque.ramon.promobusqueapp.maps.EnderecoServico
import promobusque.ramon.promobusqueapp.modelos.Empresa
import kotlin.jvm.internal.Intrinsics
import com.google.android.gms.maps.SupportMapFragment



class GoogleMapsActivity : OnMapReadyCallback, FragmentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_google_maps)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    override fun onMapReady(nMap: GoogleMap?) {
        val empresa = intent.getSerializableExtra("empresa") as Empresa

        val enderecoServico = EnderecoServico(this)
        enderecoServico.BuscaDadosPeloNome(empresa.Endereco)

        val latitude = enderecoServico.latitude
        val longitude = enderecoServico.longitude
        val endereco2 = enderecoServico.endereco

        if (nMap != null) {
            val locationPoint = LatLng(latitude, longitude)
            nMap!!.addMarker(MarkerOptions().position(LatLng(latitude, longitude)).title(endereco2))
            nMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationPoint, 15.5f))
        }
    }
}