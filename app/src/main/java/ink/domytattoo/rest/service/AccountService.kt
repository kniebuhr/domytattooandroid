package ink.domytattoo.rest.service

import ink.domytattoo.rest.request.SignInBody
import ink.domytattoo.rest.request.SignUpBody
import ink.domytattoo.rest.response.AccountModel
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
interface AccountService {

    @POST("signin/")
    fun signin(@Body body : SignInBody) : Observable<AccountModel.SignIn>

    @POST("signup/")
    fun signup(@Body body : SignUpBody) : Observable<AccountModel.SignIn>

    companion object {
        fun create(): AccountService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                        RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                        GsonConverterFactory.create())
                .baseUrl("https://do-my-tattoo.herokuapp.com/account/")
                .build()

            return retrofit.create(AccountService::class.java)
        }
    }
}