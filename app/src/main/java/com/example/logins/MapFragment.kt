package com.example.logins

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import com.example.logins.util.Constants
import com.example.logins.util.LocationPermissionHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.android.gestures.RotateGestureDetector
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.OnRotateListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location
import java.lang.ref.WeakReference


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER



class MapFragment() : Fragment(), ItemClickListener {
    lateinit  var mapView: MapView
    private  lateinit var mapStyle: String
    private lateinit  var userLocation : ImageView
    private lateinit var locationPermissionHelper: LocationPermissionHelper
    private lateinit var mapTiles: FloatingActionButton

    private val onIndicatorBearingChangedListener = OnIndicatorBearingChangedListener {
        mapView.getMapboxMap().setCamera(CameraOptions.Builder().bearing(it).build())
    }

    private lateinit var point: Point
    private val onIndicatorPositionChangedListener = OnIndicatorPositionChangedListener {
        point = it
        mapView.getMapboxMap().setCamera(CameraOptions.Builder().center(it).build())
        mapView.gestures.focalPoint = mapView.getMapboxMap().pixelForCoordinate(it)
    }

    fun changeTile (str: String) {

        mapView.getMapboxMap().loadStyleUri(str)
    }
    private val onMoveListener = object : OnMoveListener {
        override fun onMoveBegin(detector: MoveGestureDetector) {
            onCameraTrackingDismissed()
        }

        override fun onMove(detector: MoveGestureDetector): Boolean {
            return false
        }

        override fun onMoveEnd(detector: MoveGestureDetector) {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        var view: View = inflater.inflate(R.layout.fragment_map, container, false)
        mapView = view.findViewById(R.id.mapView)
        userLocation = view.findViewById(R.id.user_location)
        mapTiles = view.findViewById(R.id.btnCenter)
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        mapStyle = sharedPref?.getString(Constants.KEY_MAP_TILES,Style.MAPBOX_STREETS).toString()

        mapView.getMapboxMap().setCamera(CameraOptions.Builder()
            .build())
        locationPermissionHelper = LocationPermissionHelper(WeakReference(context as Activity?))
        locationPermissionHelper.checkPermissions {

            onMapReady()
        }

        userLocation.setOnClickListener{
            mapView.getMapboxMap().setCamera(CameraOptions.Builder().center(point).build())
        }

        mapTiles.setOnClickListener{
            showBottomSheetDialogFragment()
        }
        return view

    }



    private fun showBottomSheetDialogFragment() {
        val mapBottomSheetFragment = MapBottomSheet.newInstance(this)
        activity?.supportFragmentManager?.let { mapBottomSheetFragment.show(it, MapBottomSheet.TAG) }
    }

    private fun onMapReady() {
        mapView.getMapboxMap().setCamera(
            CameraOptions.Builder()
                .zoom(13.0)
                .build()
        )
        mapView.getMapboxMap().loadStyleUri(
           mapStyle
        ) {
            initLocationComponent()
            setupGesturesListener()
        }

    }

    private fun setupGesturesListener() {
        mapView.gestures.addOnMoveListener(onMoveListener)
    }
    private fun initLocationComponent() {

        val locationComponentPlugin = mapView.location

        locationComponentPlugin.updateSettings {
            this.enabled = true
            this.locationPuck = LocationPuck2D(
                topImage = context?.let {
                    AppCompatResources.getDrawable(
                        it,
                        R.drawable.location_360
                    )
                },
                bearingImage = context?.let {
                    AppCompatResources.getDrawable(
                        it,
                        com.mapbox.maps.plugin.locationcomponent.R.drawable.mapbox_user_bearing_icon
                    )
                },
                shadowImage = context?.let {
                    AppCompatResources.getDrawable(
                        it,
                        com.mapbox.maps.plugin.viewport.R.drawable.mapbox_user_icon_shadow,
                    )
                },
                scaleExpression = interpolate {
                    linear()
                    zoom()
                    stop {
                        literal(0.0)
                        literal(0.6)
                    }
                    stop {
                        literal(20.0)
                        literal(1.0)
                    }
                }.toJson()
            )
        }

        locationComponentPlugin.addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        locationComponentPlugin.addOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener);


    }


    private fun onCameraTrackingDismissed() {
        Toast.makeText(context, "onCameraTrackingDismissed", Toast.LENGTH_SHORT).show()
        mapView.location
            .removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        mapView.location
            .removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
        mapView.gestures.removeOnMoveListener(onMoveListener)

    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.location
            .removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
        mapView.location
            .removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        mapView.gestures.removeOnMoveListener(onMoveListener)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    val rotateListener = object : OnRotateListener {
        override fun onRotateBegin(@NonNull detector: RotateGestureDetector) {
            // Do something when the user starts rotating the map.
        }

        override fun onRotate(@NonNull detector: RotateGestureDetector) {
            // Do something while the user rotates the map.
        }

        override fun onRotateEnd(@NonNull detector: RotateGestureDetector) {
            // Do something when the user stops rotating the map.
        }
    }
    override fun onItemClick(item: String?) {
        if (item != null) {
            mapView.getMapboxMap().loadStyleUri(item)
            mapTilesShare(item)
        }
        else {
            Toast.makeText(context, "onItemClick is null", Toast.LENGTH_SHORT).show()
        }

    }
    private fun mapTilesShare (string: String){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString(Constants.KEY_MAP_TILES, string)

            apply()
        }

    }
}
