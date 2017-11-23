package ink.domytattoo.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ink.domytattoo.ImageHelper
import ink.domytattoo.R
import ink.domytattoo.activity.FragmentActivity
import ink.domytattoo.rest.response.FlashworkModel
import ink.domytattoo.rest.response.SearchModel
import kotlinx.android.synthetic.main.item_flashwork.view.*
import java.text.NumberFormat
import java.util.*

/**
 * Created by knieb on 11/11/2017.
 */
class FlashAdapter (private val flashworks : List<FlashworkModel.Flashwork>,
                    private val context : Context,
                    val clickListener: onClicked) : Adapter<FlashAdapter.ViewHolder>() {

    companion object {
        var mClickListener: onClicked? = null
    }

    open interface onClicked{
        fun onFlashClicked(flash: FlashworkModel.Flashwork)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_flashwork, parent, false)
        return FlashAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.let {
            mClickListener = clickListener
            it.bindView(flashworks[position])
        }
    }

    override fun getItemCount(): Int {
        return flashworks.size
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        fun bindView(flash: FlashworkModel.Flashwork){

            itemView.setOnClickListener {
                if(mClickListener !=  null)
                    mClickListener!!.onFlashClicked(flash)
            }

            var format = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

            val image = itemView.flash_image_one
            val artist = itemView.flash_artist_one
            val price = itemView.flash_price_one
            val auction = itemView.flash_auction_one
            val card = itemView.flash_card_one

            if(!flash.images.isEmpty()) ImageHelper(image).execute(flash.images[0].url)
            artist.text = flash.tattooArtist.name
            price.text = format.format(if (flash.isAuction) { if (!flash.allBids.isEmpty()) flash.lastBid.price else 0 } else flash.price)
            auction.visibility = if (flash.isAuction) View.VISIBLE else View.GONE
            card.setBackgroundResource(if(flash.isAuction) R.color.blue else R.color.black3)
        }
    }
}
