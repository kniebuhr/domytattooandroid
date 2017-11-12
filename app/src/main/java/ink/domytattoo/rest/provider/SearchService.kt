package ink.domytattoo.rest.provider

import ink.domytattoo.rest.response.Model
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by knieb on 11/11/2017.
 */
interface SearchService {

    @GET("searchTattooArtists/{search}")
    fun searchTattooArtists(@Path("search") search : String) : Observable<List<Model.SearchArtistResponse>>

    companion object {
        fun create(): SearchService {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(
                            RxJava2CallAdapterFactory.create())
                    .addConverterFactory(
                            GsonConverterFactory.create())
                    .baseUrl("https://do-my-tattoo.herokuapp.com/search/")
                    .build()

            return retrofit.create(SearchService::class.java)
        }
    }
}