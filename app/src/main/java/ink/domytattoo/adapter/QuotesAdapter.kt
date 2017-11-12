package ink.domytattoo.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ink.domytattoo.R
import ink.domytattoo.rest.response.QuotesModel

/**
 * Created by knieb on 12/11/2017.
 */
class QuotesAdapter(private val artists : List<QuotesModel.Artist>,
                    private val context : Context) : RecyclerView.Adapter<QuotesAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val artist = artists[position]

        holder?.let {
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

        fun bindView(artist: QuotesModel.Artist){

        }
    }
}