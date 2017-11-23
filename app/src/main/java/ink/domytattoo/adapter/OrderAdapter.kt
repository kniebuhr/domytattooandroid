package ink.domytattoo.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ink.domytattoo.ImageHelper
import ink.domytattoo.R
import ink.domytattoo.rest.response.FlashworkModel
import ink.domytattoo.rest.response.OrderModel
import kotlinx.android.synthetic.main.item_flashwork.view.*
import kotlinx.android.synthetic.main.item_quote.view.*
import java.text.NumberFormat
import java.util.*

/**
 * Created by knieb on 12/11/2017.
 */
class OrderAdapter(private val quotes : List<OrderModel.Quote>,
                   private val context : Context,
                   val clickListener: onClicked) : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    companion object {
        var mClickListener: onClicked? = null
    }

    open interface onClicked{
        fun onQuoteClicked(quote: OrderModel.Quote)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_quote, parent, false)
        return OrderAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.let {
            mClickListener = clickListener
            it.bindView(quotes[position])
        }
    }

    override fun getItemCount(): Int {
        return quotes.size
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        fun bindView(quote: OrderModel.Quote){

            if(quote.negotiations.isEmpty()) return

            itemView.setOnClickListener {
                if(mClickListener !=  null)
                    mClickListener!!.onQuoteClicked(quote)
            }

            var format = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

            val image = itemView.image
            val username = itemView.username
            val dimensions = itemView.dimensions
            val description = itemView.description
            val place = itemView.place
            val price = itemView.price
            val message = itemView.message_icon

            if(!quote.negotiations[0].status.viewed) message.visibility = View.VISIBLE else message.visibility = View.GONE

            if (quote.images.isNotEmpty()) ImageHelper(image).execute(quote.images[0].url) else image.setImageResource(R.drawable.ic_menu_camera)
            username.text = quote.negotiations[0].tattooArtist.name
            dimensions.text = quote.height.toString() + " x " + quote.width.toString()
            description.text = quote.description
            place.text = quote.place

            price.text = if (quote.negotiations[0].price != null) format.format(quote.negotiations[0].price) else "Pendente"
        }
    }
}