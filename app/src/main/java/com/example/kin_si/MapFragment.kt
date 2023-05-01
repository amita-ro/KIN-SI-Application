package com.example.kin_si

import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment
import com.esri.arcgisruntime.geometry.*
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.Basemap
import com.esri.arcgisruntime.mapping.Viewpoint
import com.esri.arcgisruntime.mapping.view.Graphic
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay
import com.esri.arcgisruntime.mapping.view.MapView
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol
import com.esri.arcgisruntime.symbology.SimpleFillSymbol
import com.esri.arcgisruntime.symbology.SimpleLineSymbol
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol
import com.example.kin_si.Adapter.ShopPointAdapter
import com.example.kin_si.Adapter.ShopPointViewItem
import com.example.kin_si.Adapter.ShopPointViewHolder


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MapFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MapFragment : Fragment() {
    private lateinit var shopPointList: List<ShopPointViewItem>
    private lateinit var mMapView: MapView
    private lateinit var graphicLayer: GraphicsOverlay
    private lateinit var shopPointAdapter: ShopPointAdapter
    private lateinit var shopViewPager2: ViewPager2
    private var isFirst: Boolean = true
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
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mMapView = view.findViewById<MapView>(R.id.mapView);
        val latitude = 13.700547
        val longitude = 100.535619
        val levelOfDetail = 15
        val map = ArcGISMap(Basemap.Type.DARK_GRAY_CANVAS_VECTOR, latitude, longitude, levelOfDetail)
        mMapView.map = map
        mMapView.isAttributionTextVisible = false

        addGraphics()
        // Create List of Page
        shopPointList = listOf<ShopPointViewItem>(
            ShopPointViewItem("Let's Say Cafe", "11/56 Soi Phetchaburi 47 · 02 821 5889", R.drawable.onboarding1, genShopPoint(100.54042709066394, 13.767451079777821)),
            ShopPointViewItem("Holey Bakery", "21/56 Soi Phetchaburi 47 · 02 821 5889", R.drawable.onboarding2, genShopPoint(100.53534747631961, 13.731109720859939)),
            ShopPointViewItem("PRESSED CAFE", "31/56 Soi Phetchaburi 47 · 02 821 5889", R.drawable.onboarding3, genShopPoint(100.56930856781713, 13.763454374559018)),
            ShopPointViewItem("Factory Coffee", "31a/56 Soi Phetchaburi 47 · 02 821 5889", R.drawable.onboarding1, genShopPoint(100.5362486044525, 13.764548288640453)),
            ShopPointViewItem("Anchan Cafe", "31b/56 Soi Phetchaburi 47 · 02 821 5889", R.drawable.onboarding2, genShopPoint(100.56133541385056, 13.804387004304488)),
            ShopPointViewItem("The Garlic Restaurant", "31c/56 Soi Phetchaburi 47 · 02 821 5889", R.drawable.onboarding3, genShopPoint(100.56322203673305, 13.811365969836904)),
            ShopPointViewItem("Stand Alone Coffee Bar", "31d/56 Soi Phetchaburi 47 · 02 821 5889", R.drawable.onboarding1, genShopPoint(100.56319791391256, 13.80826735290282)),
            ShopPointViewItem("See You Latte Cafe", "31e/56 Soi Phetchaburi 47 · 02 821 5889", R.drawable.onboarding2, genShopPoint(100.56865690492445, 13.803294836250421)),
            ShopPointViewItem("SWEET SPELL", "31f/56 Soi Phetchaburi 47 · 02 821 5889", R.drawable.onboarding3, genShopPoint(100.587689681857, 13.77840046375826)),
            ShopPointViewItem("2U Bakery", "31f/56 Soi Phetchaburi 47 · 02 821 5889", R.drawable.onboarding3, genShopPoint(100.56257105912381, 13.777979114414686))
        )
        // find Extent of all point
        val listOfPoint = mutableListOf<Point>()
        shopPointList.forEach {
            listOfPoint.add(it.location)
        }
        var mCompleteExtent: Envelope = GeometryEngine.combineExtents(listOfPoint);
        var newX1 = mCompleteExtent.xMin - mCompleteExtent.xMin*0.0001
        var newY1 = mCompleteExtent.yMin - mCompleteExtent.yMin*0.0001
        var newX2 = mCompleteExtent.xMax + mCompleteExtent.xMax*0.0001
        var newY2 = mCompleteExtent.yMax + mCompleteExtent.yMax*0.0001
        var mExtent2 = Envelope(newX1, newY1, newX2, newY2, mCompleteExtent.spatialReference)

        shopViewPager2 = view.findViewById<ViewPager2>(R.id.shopViewPager2);
        createBuffer7km()
        shopPointAdapter = ShopPointAdapter(shopPointList)
        shopViewPager2.adapter = shopPointAdapter;
        shopPointAdapter = ShopPointAdapter(shopPointList)
        shopViewPager2 = view.findViewById<ViewPager2>(R.id.shopViewPager2);
        shopViewPager2.adapter = shopPointAdapter;
        shopViewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (isFirst) {
                    isFirst = false;
                    addCurrentLocation(mExtent2)
                } else mMapView.setViewpointCenterAsync(shopPointList[position].location, 30000.0)
            }
        })

    }

    private fun addGraphics() {
        // create a graphics overlay and add it to the map view
        graphicLayer = GraphicsOverlay()
        mMapView.graphicsOverlays.add(graphicLayer)
    }

    private fun addPoint() {
        // create a point geometry with a location and spatial reference
        // Point(latitude, longitude, spatial reference)
        val point = Point(100.56892960569901, 13.761952685466564, SpatialReferences.getWgs84())

        // create an opaque orange (0xFFFF5733) point symbol with a blue (0xFF0063FF) outline symbol
        val simpleMarkerSymbol = SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.YELLOW, 10f)
        val blueOutlineSymbol = SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.RED, 2f)
        simpleMarkerSymbol.outline = blueOutlineSymbol
        // create a graphic with the point geometry and symbol
        val pointGraphic = Graphic(point, simpleMarkerSymbol)

        // add the point graphic to the graphics overlay
        graphicLayer.graphics.add(pointGraphic)
    }

    private fun createBuffer7km() {
        val currentLocationPoint = Point(100.56892960569901, 13.761952685466564, SpatialReferences.getWgs84())
        // create buffer polygon
        // create buffer geometry 100 meter
        val geometryBuffer = GeometryEngine.bufferGeodetic(currentLocationPoint, 7.0,
                LinearUnit(LinearUnitId.KILOMETERS), Double.NaN, GeodeticCurveType.GEODESIC)

        // create symbol for buffer geometry
        val geodesicOutlineSymbol = SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.BLACK, 2F)
        // 0x4D00FF00 = Green Color with 25% Opacity (4D = 25%)
        val geodesicBufferFillSymbol = SimpleFillSymbol(SimpleFillSymbol.Style.SOLID,
                0x4D00FF00.toInt(), geodesicOutlineSymbol)

        // new graphic
        val graphicBuffer =  Graphic(geometryBuffer, geodesicBufferFillSymbol)
        //graphicLayer.graphics.add(graphicBuffer)

        // filter 7km shop
        shopPointList = shopPointList.filter {
            var isInside = GeometryEngine.intersects(geometryBuffer, it.location)
            isInside
        }
        // add all filtered shop to map
        shopPointList.forEach {
            genShopPoint(it.location.x, it.location.y, true)
        }
    }

    private fun addCurrentLocation(extent: Envelope) {
        val currentLocationPoint = Point(100.56592960569901, 13.761952685466564, SpatialReferences.getWgs84())
        // create an opaque orange (0xFFFF5733) point symbol with a blue (0xFF0063FF) outline symbol
        val simpleMarkerSymbol = SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.YELLOW, 10f)
        val blueOutlineSymbol = SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.RED, 2f)
        simpleMarkerSymbol.outline = blueOutlineSymbol
        // create a graphic with the point geometry and symbol
        val pointGraphic = Graphic(currentLocationPoint, simpleMarkerSymbol)
        // add the point graphic to the graphics overlay
        graphicLayer.graphics.add(pointGraphic)
        // zoom to current location point
        mMapView.setViewpointCenterAsync(currentLocationPoint, 30000.0)
        mMapView.setViewpointAsync(Viewpoint(extent));

        // create buffer polygon
        // create buffer geometry 100 meter
        val geometryBuffer = GeometryEngine.bufferGeodetic(currentLocationPoint, 7.0,
                LinearUnit(LinearUnitId.KILOMETERS), Double.NaN, GeodeticCurveType.GEODESIC)

        // create symbol for buffer geometry
        val geodesicOutlineSymbol = SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.BLACK, 2F)
        // 0x4D00FF00 = Green Color with 25% Opacity (4D = 25%)
        val geodesicBufferFillSymbol = SimpleFillSymbol(SimpleFillSymbol.Style.SOLID,
                0x4D00FF00.toInt(), geodesicOutlineSymbol)

        // new graphic
        val graphicBuffer =  Graphic(geometryBuffer, geodesicBufferFillSymbol)
        //graphicLayer.graphics.add(graphicBuffer)
    }


    private fun genShopPoint(x: Double, y: Double, addToMap: Boolean = false): Point {
        // create a point geometry with a location and spatial reference
        // Point(latitude, longitude, spatial reference)
        val point = Point(x, y, SpatialReferences.getWgs84())
        if (addToMap) {
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
        return point
    }

    private fun addPointPictureSymbol() {
        // create a point geometry with a location and spatial reference
        // Point(latitude, longitude, spatial reference)
        val point = Point(100.54123476818575, 13.692994274846674, SpatialReferences.getWgs84())
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


    private fun addLine() {
        // create a point collection with a spatial reference, and add three points to it
        val polylinePoints = PointCollection(SpatialReferences.getWgs84()).apply {
            // Point(latitude, longitude)
            add(Point(100.54123476818575, 13.710493830770453))
            add(Point(100.53798546999967, 13.708337727157508))
        }

        // create a polyline geometry from the point collection
        val polyline = Polyline(polylinePoints)

        // create a blue line symbol for the polyline
        val polylineSymbol = SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.parseColor("#EA9035"), 3f)
        // create a polyline graphic with the polyline geometry and symbol
        val polylineGraphic = Graphic(polyline, polylineSymbol)

        // add the polyline graphic to the graphics overlay
        graphicLayer.graphics.add(polylineGraphic)
    }

    private fun addPolygon() {
        // create a point collection with a spatial reference, and add five points to it
        val polygonPoints = PointCollection(SpatialReferences.getWgs84()).apply {
            // Point(latitude, longitude)
            add(Point(100.53113129005246, 13.699605900794168))
            add(Point(100.52806591440523, 13.697497348388572))
            add(Point(100.52862994352431, 13.693494620610096))
            add(Point(100.53566804601036, 13.691517057334085))
            add(Point(100.53510401689125, 13.69565086041018))
        }
        // create a polygon geometry from the point collection
        val polygon = Polygon(polygonPoints)
        val blueOutlineSymbol = SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.YELLOW, 2f)
        // create an orange fill symbol with 20% transparency and the blue simple line symbol
        val polygonFillSymbol =
            SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, Color.parseColor("#FB848A"), blueOutlineSymbol)

        // create a polygon graphic from the polygon geometry and symbol
        val polygonGraphic = Graphic(polygon, polygonFillSymbol)
        // add the polygon graphic to the graphics overlay
        graphicLayer.graphics.add(polygonGraphic)
    }

    override fun onPause() {
        super.onPause()
        mMapView.pause()
    }

    override fun onResume() {
        super.onResume()
        mMapView.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView.dispose()
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MapFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                MapFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}