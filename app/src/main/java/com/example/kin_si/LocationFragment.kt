package com.example.kin_si

import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment
import com.esri.arcgisruntime.geometry.Point
import com.esri.arcgisruntime.geometry.SpatialReferences
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.Basemap
import com.esri.arcgisruntime.mapping.view.Graphic
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay
import com.esri.arcgisruntime.mapping.view.MapView
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol
import com.esri.arcgisruntime.symbology.SimpleLineSymbol
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol
import com.example.kin_si.utils.GlobalBox

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LocationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LocationFragment : Fragment() {
    private lateinit var mMapView: MapView
    private lateinit var graphicLayer: GraphicsOverlay
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        ArcGISRuntimeEnvironment.setLicense(resources.getString(R.string.arcgis_license_key))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mMapView = view.findViewById<MapView>(R.id.mapView);
        val latitude = GlobalBox.Latitude
        val longitude = GlobalBox.Longitude
        val levelOfDetail = 17
        val map = ArcGISMap(Basemap.Type.DARK_GRAY_CANVAS_VECTOR, latitude, longitude, levelOfDetail)
        mMapView.map = map
        mMapView.isAttributionTextVisible = false
        addGraphics()
        genShopPoint(longitude, latitude)
    }

    private fun addGraphics() {
        // create a graphics overlay and add it to the map view
        graphicLayer = GraphicsOverlay()
        mMapView.graphicsOverlays.add(graphicLayer)
    }

    private fun genShopPoint(x: Double, y: Double) {
        // create a point geometry with a location and spatial reference
        // Point(latitude, longitude, spatial reference)
        val point = Point(x, y, SpatialReferences.getWgs84())
        // code for  get image in drawable folder
        val pictureMarkerSymbol = PictureMarkerSymbol.createAsync(
                ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.marker
                ) as BitmapDrawable
        ).get()

        // set width, height, z from ground
        pictureMarkerSymbol.height = 52f
        pictureMarkerSymbol.width = 52f
        pictureMarkerSymbol.offsetY = 11f
        // create a graphic with the point geometry and symbol
        val pointGraphic = Graphic(point, pictureMarkerSymbol)

        // add the point graphic to the graphics overlay
        graphicLayer.graphics.add(pointGraphic)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LocationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                LocationFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}