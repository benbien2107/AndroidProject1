package com.example.logins

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.logins.util.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.mapbox.maps.extension.style.expressions.dsl.generated.image


class ChooseProfileDialogFragment : DialogFragment() {

    private lateinit var gridView: GridView
    private lateinit var mView: View
    private lateinit var  auth : FirebaseAuth
    private lateinit var user : FirebaseUser
    private lateinit var  firestore: FirebaseFirestore

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val inflater:LayoutInflater = this.layoutInflater
            mView = inflater.inflate(R.layout.fragment_choose_profile_dialog, null)
            gridView = mView.findViewById(R.id.gridView)

            val arr = arrayListOf<Int>()
            arr.add(Constants.PROFILE_A)
            arr.add(Constants.PROFILE_B)
            arr.add(Constants.PROFILE_C)
            arr.add(Constants.PROFILE_D)
            arr.add(Constants.PROFILE_E)
            arr.add(Constants.PROFILE_F)
            arr.add(Constants.PROFILE_G)
            arr.add(Constants.PROFILE_H)
            arr.add(Constants.PROFILE_DEFAULT)
            val arr1 = arrayOf(9)
            val adapter = ChooseProfileImageAdapter(context, arr)
            val builder = AlertDialog.Builder(it)

            gridView.adapter = adapter
            builder.setView(mView)
            builder.setMessage(R.string.choose_profile)
                .setNegativeButton("Back",
                    DialogInterface.OnClickListener { dialog, id ->
                        dialog.dismiss()

                    })
            builder.setCancelable(false)
            val result = builder.create()
            gridView.onItemClickListener = AdapterView.OnItemClickListener{ parent, view, position, id ->
                auth = FirebaseAuth.getInstance()
                user = auth.currentUser!!
                firestore = FirebaseFirestore.getInstance()
                val profilePic = arr[position]
                val docRef = firestore.collection(Constants.KEY_COLLECTION_USERS).document(user.uid)
                docRef.update(Constants.KEY_IMAGE, profilePic)
                Toast.makeText(context, "You are clicking", Toast.LENGTH_SHORT).show()
                result.dismiss()
                requireActivity().finish()
                startActivity(requireActivity().intent)
            }
            // Create the AlertDialog object and return it
            result
        } ?: throw IllegalStateException("Activity cannot be null")

    }


}