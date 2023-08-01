package com.example.logins.users

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.logins.R
import com.example.logins.chat.ChatActivity
import com.example.logins.chat.ChatCustomAdapter
import com.example.logins.util.ChatItemClickListener
import com.example.logins.util.Constants

class UserListAdapter (  private val dataSet: List<User>) :
    RecyclerView.Adapter<UserListAdapter.ViewHolder>()  {
    constructor(context: Context, users: List<User>) : this(dataSet = users) {

    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var emailView: TextView
        var nameView : TextView
        var profileImage: ImageView
        lateinit var chatItemClickListener: ChatItemClickListener



        init {
            // Define click listener for the ViewHolder's View
            nameView = view.findViewById(R.id.name_chat)
            emailView = view.findViewById(R.id.message_chat)
            profileImage = view.findViewById(R.id.pic_profile)
            view.setOnClickListener(this)
        }

        fun setItemClickListener(chatItemClickListener: ChatItemClickListener) {
            this.chatItemClickListener = chatItemClickListener
        }


        override fun onClick(view: View?) {
            chatItemClickListener.onItemClick(view,adapterPosition)
        }

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.chat_item_recyle, viewGroup, false)

        return ViewHolder(view)
    }


    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        viewHolder.emailView.text = dataSet[position].getEmail();
        viewHolder.nameView.text = dataSet[position].getName();
        viewHolder.profileImage.setImageResource(dataSet[position].getImage());
        viewHolder.setItemClickListener( object : ChatItemClickListener() {
            override fun onItemClick(view: View?, position: Int) {
                val intent = Intent(view?.context, ChatActivity::class.java)
                intent.putExtra(Constants.KEY_USER, dataSet[position].getName())
                view?.context?.startActivity(intent)
            }
        })

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size



}
