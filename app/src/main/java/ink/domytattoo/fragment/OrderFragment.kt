package ink.domytattoo.fragment

import android.content.Intent
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ink.domytattoo.Constants
import ink.domytattoo.R
import ink.domytattoo.SharedPreferencesHelper
import ink.domytattoo.activity.OrderActivity
import ink.domytattoo.adapter.OrderAdapter
import ink.domytattoo.rest.response.OrderModel
import ink.domytattoo.rest.service.OrderService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_quotes_client.*

/**
 * Created by knieb on 15/11/2017.
 */
class OrderFragment : Fragment() {
    var mView : View? = null

    val orderService by lazy {
        OrderService.create()
    }

    var disposable: Disposable? = null

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mView = inflater!!.inflate(R.layout.fragment_quotes_client, container, false)

        disposable =
                orderService.getCustomerOrders(SharedPreferencesHelper.getInstance().getString(Constants().EXTRA_USER_ID))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { result -> setupRecyclerView(result)
                                },
                                { error -> Toast.makeText(context, error.localizedMessage, Toast.LENGTH_SHORT).show() }
                        )

        return mView
    }

    private fun setupRecyclerView(quotes: List<OrderModel.Quote>){
        val mRecyclerView = quote_recycler_view
        mRecyclerView.adapter = OrderAdapter(quotes, context, object : OrderAdapter.onClicked {
            override fun onQuoteClicked(quote: OrderModel.Quote) {
                var intent = Intent(context, OrderActivity::class.java)
                intent.putExtra(Constants().EXTRA_QUOTE, quote)
                startActivity(intent)
            }
        })
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        mRecyclerView.layoutManager = layoutManager
    }
}