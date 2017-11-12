package ink.domytattoo.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ink.domytattoo.R
import ink.domytattoo.adapter.FlashAdapter
import ink.domytattoo.rest.response.SearchModel
import ink.domytattoo.rest.service.SearchService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_flash.*

class FlashworkFragment : Fragment() {

    var mView : View? = null

    val searchService by lazy {
        SearchService.create()
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
                searchService.searchFlashworks("59ec089fea70e5078df59955")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { result -> setupRecyclerView(result)
                                },
                                { error -> Toast.makeText(context, error.localizedMessage, Toast.LENGTH_SHORT).show() }
                        )

        return mView
    }

    private fun setupRecyclerView(flashworks: List<SearchModel.Flashwork>){
        val mRecyclerView = flash_recycler_view
        mRecyclerView.adapter = FlashAdapter(flashworks, context)
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        mRecyclerView.layoutManager = layoutManager
    }
}
