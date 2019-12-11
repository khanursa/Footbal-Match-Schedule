package kade.submission2.footballmatchschedule.presenter

import kade.submission2.footballmatchschedule.contract.EventSearchContract
import kade.submission2.footballmatchschedule.model.Event
import kade.submission2.footballmatchschedule.model.SearchEventResponse
import kade.submission2.footballmatchschedule.service.ApiFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class EventSearchPresenterTest {

    @Mock
    lateinit var presenter: EventSearchPresenter

    @Mock
    lateinit var service: ApiFactory

    @Mock
    lateinit var view: EventSearchContract.View

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = EventSearchPresenter(view)
    }

    // search match
    @Test
    fun eventSearch() {
        val event: MutableList<Event> = mutableListOf()
        val response = SearchEventResponse(event)
        val value = "juventus"
        GlobalScope.launch {
            Mockito.`when`(
                service.sportdbApi.getSearchEvent(value)
                    .test()
                    .assertSubscribed()
                    .assertValue(response)
                    .assertComplete()
                    .assertNoErrors()
            )
            presenter.EventSearch(value)

            Mockito.verify(presenter).getEventSearch()
            Mockito.verify(view).loadSearchEventSuccess()
        }
    }
}