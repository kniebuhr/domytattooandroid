package ink.domytattoo.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ink.domytattoo.R
import ink.domytattoo.adapter.FlashAdapter
import kotlinx.android.synthetic.main.fragment_flash.*

class FlashworkFragment : Fragment() {

    var mRecyclerView : RecyclerView? = null
    var mView : View? = null
    var mFlashAdapter : FlashAdapter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mView = inflater!!.inflate(R.layout.fragment_flash, container, false)
        mRecyclerView = flash_recycler_view
        return mView
    }

    private fun setupRecyclerView(){
        mRecyclerView = mView!!.findViewById(R.id.flash_recycler_view)
        mRecyclerView!!.setHasFixedSize(true)
        mRecyclerView!!.setAdapter(mFlashAdapter)
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        mRecyclerView!!.layoutManager = layoutManager
    }
}
