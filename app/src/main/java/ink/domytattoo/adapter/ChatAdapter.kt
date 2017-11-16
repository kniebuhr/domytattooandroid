package ink.domytattoo.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ink.domytattoo.R
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
            var text = itemView.chat_message
            var container = itemView.chat_container

            text.text = message.message
            if(message.sender!!.name.equals("Cliente")){
                container.setBackgroundResource(R.drawable.bg_chat_left)

            } else {
                container.setBackgroundResource(R.drawable.bg_chat_right)
            }
        }
    }
}