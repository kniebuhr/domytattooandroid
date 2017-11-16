package ink.domytattoo.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import ink.domytattoo.Constants
import ink.domytattoo.R
import ink.domytattoo.adapter.ChatAdapter
import ink.domytattoo.rest.response.OrderModel
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.toolbar.*

class ChatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        setSupportActionBar(toolbar)
        title = "Chat"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        var negotiation = intent.extras.getSerializable(Constants().EXTRA_CHAT) as OrderModel.Negotiation

        setupRecyclerView(negotiation.chat)
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
