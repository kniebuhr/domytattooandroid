package ink.domytattoo.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ink.domytattoo.R
import ink.domytattoo.rest.response.SearchModel
import kotlinx.android.synthetic.main.item_artist_search.view.*

/**
 * Created by knieb on 12/11/2017.
 */
class ArtistAdapter(private val artists : List<SearchModel.Artist>,
                    private val context : Context,
                    val clickListener: onClicked) : Adapter<ArtistAdapter.ViewHolder>() {

    companion object {
        var mClickListener: onClicked? = null
    }

    open interface onClicked{
        fun onArtistClicked(artist : SearchModel.Artist)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val artist = artists[position]

        holder?.let {
            mClickListener = clickListener
            it.bindView(artist)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_artist_search, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return artists.size
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        fun bindView(artist: SearchModel.Artist){

            itemView.setOnClickListener {
                if(mClickListener !=  null)
                    mClickListener!!.onArtistClicked(artist)
            }

            val name = itemView.item_artist_name
            val user = itemView.item_artist_user
            val photo = itemView.item_artist_photo
            val instagram = itemView.item_artist_instagram
            val localion = itemView.item_artist_location
            val styles = itemView.item_artist_styles

            name.text = artist.name
            instagram.text = artist.instagram
            localion.text = artist.city + "/" + artist.state
            photo.setImageResource(R.drawable.ic_menu_camera)
            user.text = artist.userName
            styles.text = "Blackwork"
        }
    }
}