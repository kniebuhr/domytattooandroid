package ink.domytattoo.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.widget.Toast
import ink.domytattoo.Constants
import ink.domytattoo.R
import ink.domytattoo.adapter.FlashAdapter
import ink.domytattoo.adapter.InstagramAdapter
import ink.domytattoo.rest.response.FlashworkModel
import ink.domytattoo.rest.response.SearchModel
import ink.domytattoo.rest.service.AccountService
import ink.domytattoo.rest.service.FlashworkService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_artist_profile.*
import kotlinx.android.synthetic.main.fragment_flash.*
import kotlinx.android.synthetic.main.toolbar.*

class ArtistProfileActivity : AppCompatActivity() {

    var artist : SearchModel.Artist? = null

    val flashworkService by lazy {
        FlashworkService.create()
    }

    val accountService by lazy {
        AccountService.create()
    }

    var disposable: Disposable? = null

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artist_profile)
        setSupportActionBar(toolbar)
        title = "Perfil"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        getExtras()
        setLayout()

        disposable =
                flashworkService.getArtistFlashworks(artist!!._id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { result -> setupRecyclerView(result)
                                },
                                { error -> Toast.makeText(this, error.localizedMessage, Toast.LENGTH_SHORT).show() }
                        )

        disposable =
                accountService.getInstaWorks(artist!!._id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> setupInstagram(result.images)
                        },
                        { error -> Toast.makeText(this, error.localizedMessage, Toast.LENGTH_SHORT).show() }
                )

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

    private fun setupRecyclerView(flashworks: List<FlashworkModel.Flashwork>){
        val mRecyclerView = artist_flash_recycler_view
        mRecyclerView.adapter = FlashAdapter(flashworks, this, object : FlashAdapter.onClicked {
            override fun onFlashClicked(flash: FlashworkModel.Flashwork) {
                var intent = Intent(baseContext, FlashworkActivity::class.java)
                intent.putExtra(Constants().EXTRA_FLASHWORK, flash)
                startActivity(intent)
            }
        })
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        layoutManager.isMeasurementCacheEnabled = false
        mRecyclerView.layoutManager = layoutManager
    }

    private fun setupInstagram(images: List<String>){
        val mRecyclerView = artist_photo_recycler_view
        mRecyclerView.adapter = InstagramAdapter(images, this)
        val layoutManager = GridLayoutManager(this, 3)
        layoutManager.isMeasurementCacheEnabled = false
        mRecyclerView.layoutManager = layoutManager
    }
}
