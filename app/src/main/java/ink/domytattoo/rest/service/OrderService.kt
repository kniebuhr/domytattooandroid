package ink.domytattoo.rest.service

import ink.domytattoo.rest.response.OrderModel
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

/**
 * Created by knieb on 11/11/2017.
 */
interface OrderService {

    @GET("getCustomerOrders/{customerId}")
    fun getCustomerOrders(@Path("customerId") customerId : String) : Observable<List<OrderModel.Quote>>

    @Multipart
    @POST("new")
    fun createNewOrder(@Part photos : List<MultipartBody.Part>?,
                       @Part("customer") customerId: RequestBody,
                       @Part("height") height : Float,
                       @Part("width") width : Float,
                       @Part("description") description : RequestBody,
                       @Part("place") place : RequestBody,
                       @Part("artist") artist : RequestBody) : Observable<OrderModel.Quote>

    companion object {
        fun create(): OrderService {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(
                            RxJava2CallAdapterFactory.create())
                    .addConverterFactory(
                            GsonConverterFactory.create())
                    .baseUrl("https://do-my-tattoo.herokuapp.com/order/")
                    .build()

            return retrofit.create(OrderService::class.java)
        }
    }
}