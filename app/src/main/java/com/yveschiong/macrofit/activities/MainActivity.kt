package com.yveschiong.macrofit.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.util.SparseArray
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.yveschiong.easycalendar.views.MonthView
import com.yveschiong.macrofit.App
import com.yveschiong.macrofit.R
import com.yveschiong.macrofit.bus.events.DateEvents
import com.yveschiong.macrofit.extensions.*
import com.yveschiong.macrofit.fragments.FoodFragment
import com.yveschiong.macrofit.fragments.NutritionFactsFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.util.*

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    val REQUEST_CODE_ADD_NUTRITION_FACT = 1
    val RESULT_KEY = "result_key"

    private var disposable = CompositeDisposable()

    private var fragments = SparseArray<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        setMenuNavigation(R.id.nav_nutrition_facts)

        // Expand and collapse the calendar/datepicker when clicked
        datePicker.setOnClickListener {
            // Have to reset the month view month in case of selected date mismatch with the view
            // and only do so when the date picker is opened from an unexpanded state
            if (!appBarLayout.isExpanded()) {
                monthView.setMonth(monthView.selectedDay)
            }

            toggleExpandedAppBar()
        }

        fab.setOnClickListener {
            // When the fab is clicked, launch the appropriate activity
            when (supportFragmentManager.findFragmentById(R.id.fragment).tag?.toInt()) {
                R.id.nav_food -> {
                    launchActivity(AddFoodActivity::class.java)
                }
                R.id.nav_nutrition_facts -> {
                    launchActivityForResult(AddNutritionFactActivity::class.java, REQUEST_CODE_ADD_NUTRITION_FACT)
                }
            }
        }

        // Post a switched date event when the month view selects a different date
        monthView.onSelectedDayListener = MonthView.OnSelectedDayListener { day ->
            toggleExpandedAppBar()
            App.graph.bus.post(DateEvents.SwitchedDateEvent(day))
        }

        // Control the chevron clicks on the month view
        monthView.onChevronSelectedListener = object : MonthView.OnChevronSelectedListener {
            override fun onLeftChevronSelected() {
                // Set the month to a month earlier
                val month = monthView.month.start
                month.add(Calendar.MONTH, -1)
                monthView.setMonth(month)
            }

            override fun onRightChevronSelected() {
                // Set the month to a month later
                val month = monthView.month.start
                month.add(Calendar.MONTH, 1)
                monthView.setMonth(month)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE_ADD_NUTRITION_FACT -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        // Do nothing for now
                    }
                    else -> {
                        makeSnackbar(fab, getString(R.string.add_nutrition_fact_error_text))
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        // Register to event bus for switched date events
        disposable.add(App.graph.bus.listen<DateEvents.SwitchedDateEvent>()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                titleText.text = it.switchedDate.time.getNormalString()
            })
    }

    override fun onPause() {
        super.onPause()

        // Unregister from event bus
        disposable.clear()
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
        // If the month view is showing then we want to hide it
        if (appBarLayout.isExpanded()) {
            toggleExpandedAppBar()
        }

        setMenuNavigation(item.itemId)
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun setMenuNavigation(id: Int) {
        nav_view.setCheckedItem(id)
        setActionBarState(id)
        switchToFragment(id)
    }

    fun setActionBarState(id: Int) {
        datePicker.visibility = View.GONE
        when (id) {
            R.id.nav_food -> {
                datePicker.visibility = View.VISIBLE

                // Reset the month view to today
                monthView.selectedDay = Calendar.getInstance()
                titleText.text = monthView.selectedDay.time.getNormalString()
            }
            R.id.nav_nutrition_facts -> {
                titleText.text = getString(R.string.nutrition_facts)
            }
            else -> return
        }
    }

    fun switchToFragment(id: Int) {
        if (fragments.indexOfKey(id) < 0) {
            when (id) {
                R.id.nav_food -> {
                    fragments.put(id, FoodFragment.newInstance())
                }
                R.id.nav_nutrition_facts -> {
                    fragments.put(id, NutritionFactsFragment.newInstance())
                }
                else -> return
            }
        }

        replaceFragment(R.id.fragment, fragments[id]!!, id.toString())
    }
}
