package com.example.logins

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mapbox.maps.Style
import java.lang.RuntimeException


class MapBottomSheetFragment(private var mListener:ItemClickListener) : BottomSheetDialogFragment(), View.OnClickListener {



    private lateinit var nightMap: LinearLayout
    private lateinit var streetMap: LinearLayout
    private lateinit var dayMap: LinearLayout
    private lateinit var satelliteMap: LinearLayout
    private lateinit var hybridMap: LinearLayout
    private lateinit var dimenMap: LinearLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view: View = inflater.inflate(R.layout.fragment_map_bottom_sheet, container, false)
        nightMap = view.findViewById(R.id.tile_traffic_night)
        dayMap = view.findViewById(R.id.tile_traffic_day)
        streetMap = view.findViewById(R.id.tile_streets)
        satelliteMap = view.findViewById(R.id.tile_satellite)
        hybridMap = view.findViewById(R.id.tile_hybrid)
        dimenMap = view.findViewById(R.id.tile_3d)


        nightMap.setOnClickListener(this)
        dayMap.setOnClickListener(this)
        streetMap.setOnClickListener(this)
        satelliteMap.setOnClickListener(this)
        hybridMap.setOnClickListener(this)
        dimenMap.setOnClickListener(this)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onClick(v: View?) {
        if (v?.id == R.id.tile_streets) {
            mListener.onItemClick(Style.MAPBOX_STREETS)
        }
        else if (v?.id == R.id.tile_hybrid){
            mListener.onItemClick(Style.OUTDOORS)
        }
        else if (v?.id== R.id.tile_satellite){
            mListener.onItemClick(Style.SATELLITE)
        }
        else if (v?.id == R.id.tile_traffic_day){
            mListener.onItemClick(Style.TRAFFIC_DAY)
        }
        else if (v?.id == R.id.tile_traffic_night){
            mListener.onItemClick(Style.TRAFFIC_NIGHT)
        }
        else if (v?.id == R.id.tile_3d){
            mListener?.onItemClick(Style.DARK)
        }
        dismiss()
    }
    companion object{
        const val TAG = "MapBottomSheetFragment"
        fun newInstance(mListener:ItemClickListener):  MapBottomSheetFragment{

            return MapBottomSheetFragment(mListener)
        }
    }
}