package ink.domytattoo.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ink.domytattoo.Constants
import ink.domytattoo.R
import ink.domytattoo.activity.FlashworkActivity
import ink.domytattoo.adapter.FlashAdapter
import ink.domytattoo.rest.response.FlashworkModel
import ink.domytattoo.rest.service.FlashworkService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_flash.*




class FlashworkFragment : Fragment() {

    var mView : View? = null

    val flashworkService by lazy {
        FlashworkService.create()
    }

    var disposable: Disposable? = null

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mView = inflater!!.inflate(R.layout.fragment_flash, container, false)

        disposable =
                flashworkService.getRandomFlashworks()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { result -> setupRecyclerView(result)
                                },
                                { error -> Toast.makeText(context, error.localizedMessage, Toast.LENGTH_SHORT).show() }
                        )

        return mView
    }

    private fun setupRecyclerView(flashworks: List<FlashworkModel.Flashwork>){
        val mRecyclerView = flash_recycler_view
        mRecyclerView.adapter = FlashAdapter(flashworks, context, object : FlashAdapter.onClicked {
            override fun onFlashClicked(flash: FlashworkModel.Flashwork) {
                var intent = Intent(context, FlashworkActivity::class.java)
                intent.putExtra(Constants().EXTRA_FLASHWORK, flash)
                startActivity(intent)
            }
        })
        val layoutManager = GridLayoutManager(activity, 2)
        layoutManager.isMeasurementCacheEnabled = false
        mRecyclerView.layoutManager = layoutManager
    }
}
