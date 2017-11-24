package ink.domytattoo.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.widget.Toast
import ink.domytattoo.Constants
import ink.domytattoo.R
import ink.domytattoo.SharedPreferencesHelper
import ink.domytattoo.adapter.ChatAdapter
import ink.domytattoo.rest.request.ChatBody
import ink.domytattoo.rest.response.OrderModel
import ink.domytattoo.rest.service.OrderService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.toolbar.*

class ChatActivity : AppCompatActivity() {

    val orderService by lazy {
        OrderService.create()
    }

//    val accountService by lazy {
//        AccountService.create()
//    }

    var disposable: Disposable? = null

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        setSupportActionBar(toolbar)
        title = "Chat"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val negotiation = intent.extras.getSerializable(Constants().EXTRA_CHAT) as OrderModel.Negotiation
        val orderId = intent.extras.getString(Constants().EXTRA_QUOTE)

        setupRecyclerView(negotiation.chat)

        send_button.setOnClickListener {
            if(!message.text.toString().isNullOrBlank())
                disposable =
                        orderService.createNewChat(ChatBody(orderId,
                                negotiation._id,
                                SharedPreferencesHelper.getInstance().getString(Constants().EXTRA_USER_ID),
                                message.text.toString()))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        { result -> Toast.makeText(this, "Mensagem enviada", Toast.LENGTH_SHORT).show()
                                        },
                                        { error -> Toast.makeText(this, error.localizedMessage, Toast.LENGTH_SHORT).show() }
                                )
        }
    }

    private fun setupRecyclerView(messages: List<OrderModel.ChatMessage>){
        val mRecyclerView = chat_recycler_view
        mRecyclerView.adapter = ChatAdapter(messages, this)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        mRecyclerView.layoutManager = layoutManager
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
