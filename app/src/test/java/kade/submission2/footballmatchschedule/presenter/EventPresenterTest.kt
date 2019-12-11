package kade.submission2.footballmatchschedule.presenter

import kade.submission2.footballmatchschedule.contract.EventContract
import kade.submission2.footballmatchschedule.model.Event
import kade.submission2.footballmatchschedule.model.EventResponse
import kade.submission2.footballmatchschedule.service.ApiFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class EventPresenterTest {

    @Mock
    lateinit var presenter: EventPresenter

    @Mock
    lateinit var service: ApiFactory

    @Mock
    lateinit var view: EventContract.View

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = EventPresenter(view)
    }

    @Test
    fun eventSoccer() {
        val event: MutableList<Event> = mutableListOf()
        val response = EventResponse(event)
        val id = 133604
        val error = ""
        // next match
        GlobalScope.launch {
            Mockito.`when`(
                service.sportdbApi.getNextEvent(id)
                    .test()
                    .assertSubscribed()
                    .assertValue(response)
                    .assertComplete()
                    .assertNoErrors()
                    .assertErrorMessage(error)
            )

            // previous match
            Mockito.`when`(
                service.sportdbApi.getPastEvent(id)
                    .test()
                    .assertSubscribed()
                    .assertValue(response)
                    .assertComplete()
                    .assertNoErrors()
                    .assertErrorMessage(error)
            )
            presenter.eventSoccer(id)

            Mockito.verify(presenter).getNextEvent()
            Mockito.verify(presenter).getPastEvent()
            Mockito.verify(view).loadNextPastSuccess()
        }
    }
}