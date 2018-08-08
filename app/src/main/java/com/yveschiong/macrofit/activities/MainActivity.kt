package com.yveschiong.macrofit.activities

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import com.yveschiong.macrofit.R
import com.yveschiong.macrofit.extensions.getNormalString
import com.yveschiong.macrofit.extensions.isExpanded
import com.yveschiong.macrofit.extensions.replaceFragment
import com.yveschiong.macrofit.fragments.FoodFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.util.*

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var fragments = HashMap<Int, Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        titleText.text = Date().getNormalString()

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        nav_view.setCheckedItem(R.id.nav_food)
        switchFragments(R.id.nav_food)

        datePicker.setOnClickListener{ appBarLayout.setExpanded(!appBarLayout.isExpanded()) }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        switchFragments(item.itemId)
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun switchFragments(id: Int) {
        if (!fragments.containsKey(id)) {
            when (id) {
                R.id.nav_food -> {
                    fragments[id] = FoodFragment.newInstance()
                }
                else -> return
            }
        }

        replaceFragment(R.id.fragment, fragments[id]!!)
    }
}
