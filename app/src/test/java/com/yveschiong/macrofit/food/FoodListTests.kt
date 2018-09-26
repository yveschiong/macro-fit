package com.yveschiong.macrofit.food

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.verify
import com.yveschiong.macrofit.UnitTests
import com.yveschiong.macrofit.bus.EventBus
import com.yveschiong.macrofit.bus.events.DateEvents
import com.yveschiong.macrofit.bus.events.UpdateEvents
import com.yveschiong.macrofit.contracts.FoodViewContract
import com.yveschiong.macrofit.models.Food
import com.yveschiong.macrofit.presenters.FoodListPresenter
import com.yveschiong.macrofit.repositories.FoodRepository
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import java.util.*


class FoodListTests : UnitTests() {
    @Mock
    private lateinit var view: FoodViewContract.View

    @Mock
    private lateinit var foodRepository: FoodRepository

    private lateinit var bus: EventBus

    private lateinit var presenter: FoodListPresenter<FoodViewContract.View>

    @Before
    fun setup() {
        bus = EventBus(PublishSubject.create())
        presenter = FoodListPresenter(bus, foodRepository)
        presenter.onAttach(view)
    }

    @After
    fun tearDown() {
        presenter.onDetach()
    }

    private fun Calendar.setStartRange(): Calendar {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
        return this
    }

    private fun Calendar.setEndRange(): Calendar {
        setStartRange()
        add(Calendar.DATE, 1)
        add(Calendar.MILLISECOND, -1)
        return this
    }

    @Test
    fun `Fetch for food, when the time range used is today's, then should call the food repo for fetching food in date range and the view should show the list of food`() {
        val start = Calendar.getInstance().setStartRange()
        val end = Calendar.getInstance().setEndRange()
        val list = arrayListOf(Food())

        doReturn(Observable.just(list))
            .`when`(foodRepository)
            .getFoodBetweenTime(start.timeInMillis, end.timeInMillis)

        presenter.fetchFood()

        verify(foodRepository).getFoodBetweenTime(start.timeInMillis, end.timeInMillis)
        verify(view).showFood(list)
    }

    @Test
    fun `Given a food card, when clicked on a card, then the view should show the edit food activity`() {
        val food = Food()

        presenter.onCardClicked(food)

        verify(view).showEditFoodActivity(food)
    }

    @Test
    fun `Given a presenter with an attached view, when a switched date event gets posted to the bus, then should call the food repo for fetching food in date range and the view should show the list of food`() {
        val start = Calendar.getInstance().setStartRange()
        val end = Calendar.getInstance().setEndRange()
        val list = arrayListOf(Food())

        doReturn(Observable.just(list))
            .`when`(foodRepository)
            .getFoodBetweenTime(start.timeInMillis, end.timeInMillis)

        bus.post(DateEvents.SwitchedDateEvent(start))

        verify(foodRepository).getFoodBetweenTime(start.timeInMillis, end.timeInMillis)
        verify(view).showFood(list)
    }

    @Test
    fun `Given a presenter with an attached view, when an add food event gets posted to the bus, then should call the food repo for fetching food in date range and the view should show the list of food`() {
        val start = Calendar.getInstance().setStartRange()
        val end = Calendar.getInstance().setEndRange()

        // Set the food's timestamp equal to today's
        val food = Food(start.timeInMillis)
        val list = arrayListOf(food)

        doReturn(Observable.just(list))
            .`when`(foodRepository)
            .getFoodBetweenTime(start.timeInMillis, end.timeInMillis)

        bus.post(UpdateEvents.AddedFoodEvent(food))

        verify(foodRepository).getFoodBetweenTime(start.timeInMillis, end.timeInMillis)
        verify(view).showFood(list)
    }

    @Test
    fun `Given a presenter with an attached view, when an edit food event gets posted to the bus, then should call the food repo for fetching food in date range and the view should show the list of food`() {
        val start = Calendar.getInstance().setStartRange()
        val end = Calendar.getInstance().setEndRange()

        // Set the food's timestamp equal to today's
        val food = Food(start.timeInMillis)
        val list = arrayListOf(food)

        doReturn(Observable.just(list))
            .`when`(foodRepository)
            .getFoodBetweenTime(start.timeInMillis, end.timeInMillis)

        bus.post(UpdateEvents.EditedFoodEvent(food))

        verify(foodRepository).getFoodBetweenTime(start.timeInMillis, end.timeInMillis)
        verify(view).showFood(list)
    }

    @Test
    fun `Given a presenter with an attached view, when a delete food event gets posted to the bus, then should call the food repo for fetching food in date range and the view should show the list of food`() {
        val start = Calendar.getInstance().setStartRange()
        val end = Calendar.getInstance().setEndRange()

        // Set the food's timestamp equal to today's
        val food = Food(start.timeInMillis)
        val list = arrayListOf(food)

        doReturn(Observable.just(list))
            .`when`(foodRepository)
            .getFoodBetweenTime(start.timeInMillis, end.timeInMillis)

        bus.post(UpdateEvents.DeletedFoodEvent(food))

        verify(foodRepository).getFoodBetweenTime(start.timeInMillis, end.timeInMillis)
        verify(view).showFood(list)
    }
}
