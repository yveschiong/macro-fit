package com.yveschiong.macrofit.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
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
import com.yveschiong.macrofit.contracts.MainViewContract
import com.yveschiong.macrofit.extensions.isExpanded
import com.yveschiong.macrofit.extensions.launchActivity
import com.yveschiong.macrofit.extensions.launchActivityForResult
import com.yveschiong.macrofit.extensions.replaceFragment
import com.yveschiong.macrofit.fragments.FoodFragment
import com.yveschiong.macrofit.fragments.NutritionFactsFragment
import com.yveschiong.macrofit.models.NutritionFact
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.util.*
import javax.inject.Inject

class MainActivity : BaseActivity(), MainViewContract.View {

    val REQUEST_CODE_ADD_NUTRITION_FACT = 1
    val RESULT_KEY = "result_key"

    @Inject
    lateinit var presenter: MainViewContract.Presenter<MainViewContract.View>

    private var fragments = SparseArray<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        App.graph.inject(this)

        presenter.onAttach(this)

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener {
            // If the month view is showing then we want to hide it
            if (appBarLayout.isExpanded()) {
                toggleExpandedAppBar()
            }

            presenter.setMenuNavigation(it.itemId)
            drawer_layout.closeDrawer(GravityCompat.START)

            true
        }

        // We only want to set this on first entry of the activity
        if (savedInstanceState == null) {
            presenter.setMenuNavigation(R.id.nav_nutrition_facts)
        } else {
            // We still would need to set the other data relating to the action bar state
            getCurrentNavId()?.let { setActionBarState(it) }
        }

        // Expand and collapse the calendar/datepicker when clicked
        datePicker.setOnClickListener {
            presenter.onCalendarIconSelected()
        }

        fab.setOnClickListener {
            presenter.onFabClickedFrom(getCurrentNavId())
        }

        monthView.onSelectedDayListener = MonthView.OnSelectedDayListener { day ->
            presenter.onMonthViewDaySelected(day)
        }

        // Control the chevron clicks on the month view
        monthView.onChevronSelectedListener = object : MonthView.OnChevronSelectedListener {
            override fun onLeftChevronSelected() {
                // Set the month to a month earlier
                presenter.incrementMonthViewMonthBy(monthView.month.start, -1)
            }

            override fun onRightChevronSelected() {
                // Set the month to a month later
                presenter.incrementMonthViewMonthBy(monthView.month.start, 1)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE_ADD_NUTRITION_FACT -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        val nutritionFact = data?.getParcelableExtra<NutritionFact>(RESULT_KEY)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
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

    override fun toggleExpandedAppBar() {
        appBarLayout.setExpanded(!appBarLayout.isExpanded())
    }

    override fun toggleCalendarExpansion() {
        // Have to reset the month view month in case of selected date mismatch with the view
        // and only do so when the date picker is opened from an unexpanded state
        if (!appBarLayout.isExpanded()) {
            monthView.setMonth(monthView.selectedDay)
        }

        toggleExpandedAppBar()
    }

    override fun setMonthViewDay(day: Calendar) {
        monthView.selectedDay = day
    }

    override fun setMonthViewMonth(month: Calendar) {
        monthView.setMonth(month)
    }

    override fun setTitleText(text: String) {
        titleText.text = text
    }

    override fun setNavViewCheckedItem(id: Int) {
        nav_view.setCheckedItem(id)
    }

    override fun setActionBarState(id: Int) {
        datePicker.visibility = View.GONE
        when (id) {
            R.id.nav_food -> {
                datePicker.visibility = View.VISIBLE

                // Reset the month view to today
                presenter.changeMonthViewSelectedDay(Calendar.getInstance())
            }
            R.id.nav_nutrition_facts -> {
                setTitleText(getString(R.string.nutrition_facts))
            }
            else -> return
        }
    }

    override fun switchToFragment(id: Int) {
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

    override fun showActivity(id: Int?) {
        // When the fab is clicked, launch the appropriate activity
        when (id) {
            R.id.nav_food -> {
                launchActivity(AddFoodActivity::class.java)
            }
            R.id.nav_nutrition_facts -> {
                launchActivityForResult(AddNutritionFactActivity::class.java, REQUEST_CODE_ADD_NUTRITION_FACT)
            }
        }
    }

    private fun getCurrentNavId(): Int? {
        return supportFragmentManager.findFragmentById(R.id.fragment).tag?.toInt()
    }
}
