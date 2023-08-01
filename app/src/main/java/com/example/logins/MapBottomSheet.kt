package com.example.logins

class MapBottomSheet {
    companion object{
        const val TAG = "MapBottomSheetFragment"
        fun newInstance(mListener:ItemClickListener):MapBottomSheetFragment{

            return MapBottomSheetFragment(mListener)
        }
    }
}