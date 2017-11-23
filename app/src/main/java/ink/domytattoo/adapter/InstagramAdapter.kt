package ink.domytattoo.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ink.domytattoo.ImageHelper
import ink.domytattoo.R
import kotlinx.android.synthetic.main.item_instagram.view.*

/**
 * Created by knieb on 23/11/2017.
 */
class InstagramAdapter (private val images : List<String>,
                        private val context : Context) : Adapter<InstagramAdapter.ViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): InstagramAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_instagram, parent, false)
        return InstagramAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.let {
            it.bindView(images[position])
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        fun bindView(image: String){
            ImageHelper(itemView.photo).execute(image)
        }
    }
}