package ink.domytattoo.rest.service

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
interface StyleService {

    @GET("getStyles")
    fun getStyles() : Observable<List<Model.Style>>

    companion object {
        fun create(): StyleService {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(
                            RxJava2CallAdapterFactory.create())
                    .addConverterFactory(
                            GsonConverterFactory.create())
                    .baseUrl("https://do-my-tattoo.herokuapp.com/style/")
                    .build()

            return retrofit.create(StyleService::class.java)
        }
    }
}