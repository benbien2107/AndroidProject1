package com.example.logins.users

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.logins.R
import com.example.logins.util.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source

class UserListActivity : AppCompatActivity() {
    lateinit var fireStore: FirebaseFirestore
    lateinit var mAuth: FirebaseAuth
    lateinit var user: FirebaseUser
    lateinit var recyclerView: RecyclerView
    lateinit var back : AppCompatImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)
        recyclerView = findViewById<RecyclerView>(R.id.user_list_recycle)
        back = findViewById(R.id.user_list_back)
        back.setOnClickListener { finish() }
        getUsers()

    }
    private fun getUsers (){
        fireStore = FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()
        user = mAuth.currentUser!!
        var source = Source.DEFAULT
        val docRef = fireStore.collection(Constants.KEY_COLLECTION_USERS)
        docRef.get(source)
            .addOnSuccessListener { snapshot ->
                val users = arrayListOf<User>()
                Toast.makeText(this@UserListActivity, "inside addonsucess", Toast.LENGTH_SHORT).show()
                for (document in snapshot.documents) {
                    if (user.uid == document.id)
                        continue
                    val email = document.getString(Constants.KEY_EMAIL)
                    val name = document.getString(Constants.KEY_NAME)
                    val image = document.get(Constants.KEY_IMAGE) as? Int
                    val u = User(name,email,R.drawable.a);
                    users.add(u)

                }
                if (users.size > 0){
                    Toast.makeText(this@UserListActivity, "make it into if", Toast.LENGTH_SHORT).show()
                    recyclerView.layoutManager = LinearLayoutManager(this@UserListActivity)
                    recyclerView.adapter = UserListAdapter(
                        this@UserListActivity.applicationContext,
                        users
                    )
                }

            }
    }




}


