package ink.domytattoo.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ink.domytattoo.ImageHelper
import ink.domytattoo.R
import ink.domytattoo.rest.response.SearchModel
import kotlinx.android.synthetic.main.item_flashwork.view.*

/**
 * Created by knieb on 11/11/2017.
 */
class FlashAdapter (private val flashworks : List<SearchModel.Flashwork>,
                    private val context : Context) : Adapter<FlashAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_flashwork, parent, false)
        return FlashAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val flashwork1 = flashworks[position*2]
        var flashwork2 : SearchModel.Flashwork?
        try{
            flashwork2 = flashworks[position*2 + 1]
        } catch (e : Exception) {
            flashwork2 = null
        }

        holder?.let {
            it.bindView(flashwork1, flashwork2)
        }
    }

    override fun getItemCount(): Int {
        return if (flashworks.size % 2 == 0) flashworks.size / 2 else 1 + flashworks.size / 2
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        fun bindView(flash1: SearchModel.Flashwork, flash2: SearchModel.Flashwork?){
            val image1 = itemView.flash_image_one
            val artist1 = itemView.flash_artist_one
            val price1 = itemView.flash_price_one
            val auction1 = itemView.flash_auction_one
            val card1 = itemView.flash_card_one
            val image2 = itemView.flash_image_two
            val artist2 = itemView.flash_artist_two
            val price2 = itemView.flash_price_two
            val auction2 = itemView.flash_auction_two
            val card2 = itemView.flash_card_two

            ImageHelper(image1).execute(flash1.images[0].url)
            artist1.text = flash1.tattooArtist.name
            price1.text = flash1.price.toString()
            auction1.visibility = if (flash1.isAuction) View.VISIBLE else View.GONE
            card1.setBackgroundResource(if(flash1.isAuction) R.color.blue else R.color.black3)

            if (flash2 == null) {
                card2.visibility = View.GONE
                return
            }

            artist2.text = flash2.tattooArtist.name
            price2.text = flash2.price.toString()
            auction2.visibility = if (flash2.isAuction) View.VISIBLE else View.GONE
            card2.setBackgroundResource(if(flash2.isAuction) R.color.blue else R.color.black3)
        }
    }
}
