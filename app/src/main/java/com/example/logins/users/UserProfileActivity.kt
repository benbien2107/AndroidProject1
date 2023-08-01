package com.example.logins.users

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.logins.ChooseProfileDialogFragment
import com.example.logins.R
import com.example.logins.util.Constants
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class UserProfileActivity : AppCompatActivity() {

    private lateinit var addButton: MaterialButton
    private lateinit var  auth : FirebaseAuth
    private lateinit var user : FirebaseUser
    private lateinit var  emailView : TextView
    private lateinit var nameView: TextView
    private lateinit var  firestore: FirebaseFirestore
    private lateinit var imageView: ImageView
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        emailView = findViewById(R.id.profile_email)
        nameView = findViewById(R.id.profile_name)
        imageView = findViewById(R.id.profile_image)
        addButton = findViewById(R.id.profile_add)
        progressBar = findViewById(R.id.progress_bar_profile)

        isLoading(false)
        auth = FirebaseAuth.getInstance()
        user = auth.currentUser!!
        firestore = FirebaseFirestore.getInstance()

        val docRef = firestore.collection(Constants.KEY_COLLECTION_USERS).document(user.uid)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val inputEmail = document.data?.get(Constants.KEY_EMAIL) as? String
                    val inputName = document.data?.get(Constants.KEY_NAME) as? String
                    val inputImage = document.getLong(Constants.KEY_IMAGE)?.toInt()
                    emailView.text = inputEmail
                    nameView.text = inputName
                    imageView.setImageResource(inputImage!!)
                } else {
                    Log.d(user.uid, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(user.uid, "get failed with ", exception)
            }
        addButton.setOnClickListener{
            isLoading(true)
            ChooseProfileDialogFragment().show(supportFragmentManager, "ChooseProfileDialogFragment")

            isLoading(false)
        }

    }

    private fun isLoading(loading: Boolean) {
        if (loading) {
            emailView.visibility = View.INVISIBLE
            nameView.visibility = View.INVISIBLE
            imageView.visibility = View.INVISIBLE
            progressBar.visibility = View.VISIBLE
        } else {
            emailView.visibility = View.VISIBLE
            nameView.visibility = View.VISIBLE
            imageView.visibility = View.VISIBLE
            progressBar.visibility = View.INVISIBLE
        }
    }
}