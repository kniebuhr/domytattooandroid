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
import ink.domytattoo.rest.service.SearchService
import ink.domytattoo.rest.response.SearchModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_artist.*

class ArtistFragment : Fragment() {

    var mView : View? = null

    val searchService by lazy {
        SearchService.create()
    }
    var disposable: Disposable? = null

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        search_button.setOnClickListener {
            if(!search.text.toString().isNullOrBlank()){
                disposable =
                        searchService.searchTattooArtists(search.text.toString())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        { result -> setupRecyclerView(result)
                                        },
                                        { error -> Toast.makeText(context, error.localizedMessage, Toast.LENGTH_SHORT).show() }
                                )
            } else {
                Toast.makeText(context, "Digite algo no campo de busca", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mView = inflater!!.inflate(R.layout.fragment_artist, container, false)

        disposable =
                searchService.searchTattooArtists("")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { result -> setupRecyclerView(result)
                                },
                                { error -> Toast.makeText(context, error.localizedMessage, Toast.LENGTH_SHORT).show() }
                        )



        return mView
    }


    private fun setupRecyclerView(artists: List<SearchModel.Artist>){
        val mRecyclerView = artist_recycler_view
        mRecyclerView.adapter = ArtistAdapter(artists, context)
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        mRecyclerView.layoutManager = layoutManager
    }
}