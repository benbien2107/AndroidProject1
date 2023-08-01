package com.example.logins.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import com.example.logins.R
import com.example.logins.util.Constants
import com.google.android.material.textfield.TextInputEditText

class ChatActivity : AppCompatActivity() {

    private lateinit var btn_back :AppCompatImageView
    private lateinit var nameView : TextView
    private  lateinit var  editTextView: TextInputEditText
    private lateinit var info : AppCompatImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        btn_back = findViewById(R.id.chat_back)
        nameView = findViewById(R.id.some_user)
        info = findViewById(R.id.chat_info)
        editTextView = findViewById(R.id.inputMessage)
        nameView.text = intent.getStringExtra(Constants.KEY_USER)
        btn_back.setOnClickListener { this.finish() }

    }


}