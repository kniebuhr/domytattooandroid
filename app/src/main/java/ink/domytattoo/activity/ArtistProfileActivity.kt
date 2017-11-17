package ink.domytattoo.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import ink.domytattoo.Constants
import ink.domytattoo.R
import ink.domytattoo.rest.response.SearchModel
import kotlinx.android.synthetic.main.activity_artist_profile.*
import kotlinx.android.synthetic.main.toolbar.*

class ArtistProfileActivity : AppCompatActivity() {

    var artist : SearchModel.Artist? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artist_profile)
        setSupportActionBar(toolbar)
        title = "Perfil"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        getExtras()
        setLayout()

        order_button.setOnClickListener {
            var intent = Intent(this, NewOrderActivity::class.java)
            intent.putExtra(Constants().EXTRA_ARTIST, artist)
            startActivity(intent)
        }
    }

    fun getExtras(){
        artist = intent.extras.getSerializable(Constants().EXTRA_ARTIST) as SearchModel.Artist
    }

    fun setLayout(){

        artist_username.text = artist!!.userName
        artist_name.text = artist!!.name
        artist_location.text = artist!!.city + "/" + artist!!.state
        artist_instagram.text = artist!!.instagram
        artist_bio.text = artist!!.bio
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
