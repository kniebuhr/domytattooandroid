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
import ink.domytattoo.adapter.ArtistAdapter
import ink.domytattoo.adapter.FlashAdapter
import ink.domytattoo.rest.provider.SearchService
import ink.domytattoo.rest.response.Model
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_artist.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ArtistFragment : Fragment() {


    var mRecyclerView: RecyclerView? = null
    var mView : View? = null
    var mFlashAdapter : FlashAdapter? = null

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
        mView = inflater!!.inflate(R.layout.fragment_artist, container, false)

        disposable =
                searchService.searchTattooArtists("a")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { result -> setupRecyclerView(result)
                                },
                                { error -> Toast.makeText(context, error.localizedMessage, Toast.LENGTH_SHORT).show() }
                        )



        return mView
    }


    private fun setupRecyclerView(artists: List<Model.SearchArtistResponse>){
        val mRecyclerView = artist_recycler_view
        mRecyclerView.adapter = ArtistAdapter(artists, context)
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        mRecyclerView.layoutManager = layoutManager
    }
}