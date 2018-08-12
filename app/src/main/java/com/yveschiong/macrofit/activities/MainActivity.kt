package com.yveschiong.macrofit.activities

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.util.SparseArray
import android.view.Menu
import android.view.MenuItem
import com.yveschiong.macrofit.App
import com.yveschiong.macrofit.R
import com.yveschiong.macrofit.bus.events.DateEvents
import com.yveschiong.macrofit.extensions.getNormalString
import com.yveschiong.macrofit.extensions.isExpanded
import com.yveschiong.macrofit.extensions.replaceFragment
import com.yveschiong.macrofit.fragments.FoodFragment
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.util.*

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var disposable: Disposable? = null

    private var fragments = SparseArray<Fragment>()

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
        switchToFragment(R.id.nav_food)

        // Expand and collapse the calendar/datepicker when clicked
        datePicker.setOnClickListener { toggleExpandedAppBar() }

        // Post a switched date event when the month view selects a different date
        monthView.setOnSelectedDayListener { day ->
            toggleExpandedAppBar()
            App.graph.bus.post(DateEvents.SwitchedDateEvent(day))
        }
    }

    override fun onResume() {
        super.onResume()

        // Register to event bus for switched date events
        disposable = App.graph.bus.listen<DateEvents.SwitchedDateEvent>()
                .subscribe {
                    titleText.text = it.switchedDate.time.getNormalString()
                }
    }

    override fun onPause() {
        super.onPause()

        // Unregister from event bus
        disposable?.dispose()
    }

    fun toggleExpandedAppBar() {
        appBarLayout.setExpanded(!appBarLayout.isExpanded())
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
        switchToFragment(item.itemId)
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun switchToFragment(id: Int) {
        if (fragments.indexOfKey(id) < 0) {
            when (id) {
                R.id.nav_food -> {
                    fragments.put(id, FoodFragment.newInstance())
                }
                else -> return
            }
        }

        replaceFragment(R.id.fragment, fragments[id]!!)
    }
}