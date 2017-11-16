package ink.domytattoo.rest.service

import ink.domytattoo.rest.response.OrderModel
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by knieb on 11/11/2017.
 */
interface OrderService {

    @GET("getCustomerOrders/{customerId}")
    fun getCustomerOrders(@Path("customerId") customerId : String) : Observable<List<OrderModel.Quote>>

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