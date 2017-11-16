package ink.domytattoo.rest.service

import ink.domytattoo.rest.request.SignInBody
import ink.domytattoo.rest.response.AccountModel
import ink.domytattoo.rest.response.FlashworkModel
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by knieb on 11/11/2017.
 */
interface FlashworkService {

    @GET("getRandomFlashworks")
    fun getRandomFlashworks() : Observable<List<FlashworkModel.Flashwork>>

    companion object {
        fun create(): FlashworkService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                        RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                        GsonConverterFactory.create())
                .baseUrl("https://do-my-tattoo.herokuapp.com/flashwork/")
                .build()

            return retrofit.create(FlashworkService::class.java)
        }
    }
}