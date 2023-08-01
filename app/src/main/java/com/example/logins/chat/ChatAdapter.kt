package com.example.logins.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.logins.R

class ChatAdapter (private  val chatMessages: List<ChatMessage>, private val senderId: String ) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_SENT = 1
    private val VIEW_TYPE_RECEIVED = 2
    class SendMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textMessage: TextView
        private val dateTimeView: TextView

        init {
            textMessage = itemView.findViewById(R.id.sendMessages)
            dateTimeView = itemView.findViewById(R.id.sendDateTime)
        }

        fun setData (chatMessage: ChatMessage){
            textMessage.text = chatMessage.message
            dateTimeView.text = chatMessage.dateTime
        }
    }
    class ReceivedMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textMessage: TextView
        private val dateTimeView: TextView

        init {
            textMessage = itemView.findViewById(R.id.sendMessages)
            dateTimeView = itemView.findViewById(R.id.sendDateTime)
        }

        fun setData (chatMessage: ChatMessage){
            textMessage.text = chatMessage.message
            dateTimeView.text = chatMessage.dateTime
        }
    }
    override fun getItemViewType(position: Int): Int {
        return if (chatMessages[position].senderId == senderId)
            VIEW_TYPE_RECEIVED
        else
            VIEW_TYPE_SENT
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_SENT)
             SendMessageViewHolder(itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_container_send_message,parent,false))
        else
            ReceivedMessageViewHolder(itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_container_send_message,parent,false))

    }

    override fun getItemCount() = chatMessages.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_TYPE_SENT){
            (holder as? SendMessageViewHolder)?.setData(chatMessage = chatMessages[position])
        } else {
            (holder as? ReceivedMessageViewHolder)?.setData(chatMessage = chatMessages[position])
        }
    }
}