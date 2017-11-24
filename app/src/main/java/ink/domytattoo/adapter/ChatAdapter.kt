package ink.domytattoo.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ink.domytattoo.Constants
import ink.domytattoo.R
import ink.domytattoo.R.id.chat_eye
import ink.domytattoo.SharedPreferencesHelper
import ink.domytattoo.rest.response.OrderModel
import kotlinx.android.synthetic.main.item_chat.view.*

/**
 * Created by knieb on 12/11/2017.
 */
class ChatAdapter(private val messages : List<OrderModel.ChatMessage>,
                  private val context : Context) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val message = messages[position]

        holder?.let {
            if(message.sender != null)
                it.bindViewMy(message)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_chat, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        fun bindViewMy(message: OrderModel.ChatMessage){
            val text = itemView.chat_message
            val container = itemView.chat_container
            val seen = itemView.chat_eye
            val left = itemView.view_left
            val right = itemView.view_right
            val eye = itemView.chat_eye_container

            text.text = message.message
            if(!message.sender!!._id.equals(SharedPreferencesHelper.getInstance().getString(Constants().EXTRA_USER_ID))){
                container.setBackgroundResource(R.drawable.bg_chat_left)
                eye.gravity = Gravity.RIGHT
                left.visibility = View.GONE
                right.visibility = View.VISIBLE
            } else {
                container.setBackgroundResource(R.drawable.bg_chat_right)
                eye.gravity = Gravity.LEFT
                left.visibility = View.VISIBLE
                right.visibility = View.GONE
            }

            seen.setImageResource(if (message.read) R.drawable.ic_seen else R.drawable.ic_unseen)
        }
    }
}