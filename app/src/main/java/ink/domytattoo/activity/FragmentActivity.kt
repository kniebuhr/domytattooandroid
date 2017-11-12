package ink.domytattoo.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import ink.domytattoo.fragment.ArtistFragment
import ink.domytattoo.fragment.FlashworkFragment
import ink.domytattoo.R
import kotlinx.android.synthetic.main.activity_fragment.*
import kotlinx.android.synthetic.main.toolbar.*

class FragmentActivity : AppCompatActivity() {

    private val fragmentManager = supportFragmentManager
    private val fragmentFlash = FlashworkFragment()
    private val fragmentArtist = ArtistFragment()
    private val fragmentQuotes = null
    private val fragmentConfig = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        setSupportActionBar(toolbar)

        changeFragment(fragmentFlash)

        navigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_flashwork -> {
                    title = "Flashworks"
                    changeFragment(fragmentFlash)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_artist -> {
                    title = "Tatuadores"
                    changeFragment(fragmentArtist)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_quotes -> {
                    title = "Orçamentos"
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_config -> {
                    title = "Configurações"
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })
    }

    private fun changeFragment(fragment: Fragment){
//        val name = fragment.javaClass.name
//
//        val fragmentOnList = fragmentManager.popBackStackImmediate(name, 0)
//
//        if (!fragmentOnList){
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout, fragment)
            transaction.commit()
//        }
    }
}
