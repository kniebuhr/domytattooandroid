package ink.domytattoo

import android.app.Fragment
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlin.properties.Delegates

class MenuActivity : AppCompatActivity() {

    var toggle : ActionBarDrawerToggle by Delegates.notNull<ActionBarDrawerToggle>()
    var fragment : SearchFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        setupNavBar()


//        fragment = Fragment.instantiate(this@MenuActivity,
//                SearchFragment::class.java!!.getName()) as SearchFragment
//
//        fragmentManager.beginTransaction().replace(R.id.main_fragment, fragment as Fragment).commit()

    }

    fun setupNavBar(){
        setSupportActionBar(findViewById(R.id.nav_bar))

        toggle = ActionBarDrawerToggle(this, findViewById(R.id.drawer_layout), R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        findViewById<DrawerLayout>(R.id.drawer_layout).addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onBackPressed() {
        if (findViewById<DrawerLayout>(R.id.drawer_layout).isDrawerOpen(GravityCompat.START)) {
            findViewById<DrawerLayout>(R.id.drawer_layout).closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(toggle.onOptionsItemSelected(item))
            return true
        return super.onOptionsItemSelected(item)
    }
}
