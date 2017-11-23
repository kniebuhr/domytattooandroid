package ink.domytattoo.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import ink.domytattoo.Constants
import ink.domytattoo.ImageHelper
import ink.domytattoo.R
import ink.domytattoo.rest.response.OrderModel
import kotlinx.android.synthetic.main.activity_quote_detail_client.*
import kotlinx.android.synthetic.main.toolbar.*
import java.text.NumberFormat
import java.util.*

class OrderActivity : AppCompatActivity() {

    var quote : OrderModel.Quote? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quote_detail_client)
        setSupportActionBar(toolbar)
        title = "Orçamento"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        quote = intent.extras.getSerializable(Constants().EXTRA_QUOTE) as OrderModel.Quote

        var format = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

        if(quote!!.negotiations.isNotEmpty() && quote!!.negotiations[0].price != null){
            price_text.text = "Preço:"
            price_value.text = format.format(quote!!.negotiations[0].price)
        } else {
            price_text.text = ""
            price_value.text = "Pendente"
        }

        if(quote!!.images.isNotEmpty()) ImageHelper(order_photo).execute(quote!!.images[0].url)
        artist.text = quote!!.negotiations[0].tattooArtist.name
        place.text = quote!!.place
        height.text = quote!!.height.toString()
        width.text = quote!!.width.toString()
        description.text = quote!!.description
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.order, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId == R.id.nav_chat){
            var intent = Intent(this, ChatActivity::class.java)
            intent.putExtra(Constants().EXTRA_CHAT, quote!!.negotiations[0])
            intent.putExtra(Constants().EXTRA_QUOTE, quote!!._id)
            startActivity(intent)
            return true
        }
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
