package ink.domytattoo.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import ink.domytattoo.Constants
import ink.domytattoo.ImageHelper
import ink.domytattoo.R
import ink.domytattoo.rest.response.FlashworkModel
import kotlinx.android.synthetic.main.activity_flashwork.*
import kotlinx.android.synthetic.main.toolbar.*
import java.text.NumberFormat
import java.util.*

class FlashworkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flashwork)
        setSupportActionBar(toolbar)
        title = "Flashwork"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        var flash = intent.extras.getSerializable(Constants().EXTRA_FLASHWORK) as FlashworkModel.Flashwork

        var format = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

        if(flash.isAuction){
            price_text.text = "Último lance:"
            price_container.setBackgroundResource(R.color.blue)
            price_value.text = format.format(if (flash.allBids.isEmpty()) 0 else flash.currentBid.price)
            flashwork_button.text = "Fazer lance"
        } else {
            price_text.text = "Preço:"
            price_container.setBackgroundResource(R.color.black2)
            price_value.text = format.format(flash.price)
            flashwork_button.text = "Tenho interesse"
        }

        ImageHelper(flash_photo).execute(flash.images[0].url)
        artist.text = flash.tattooArtist.name

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
