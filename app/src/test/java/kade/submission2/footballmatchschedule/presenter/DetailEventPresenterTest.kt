package kade.submission2.footballmatchschedule.presenter

import kade.submission2.footballmatchschedule.contract.DetailEventContract
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

class DetailEventPresenterTest {
    @Mock
    lateinit var presenter: DetailEventPresenter

    @Mock
    lateinit var service: ApiFactory

    @Mock
    lateinit var view: DetailEventContract.View

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailEventPresenter(view)
    }

    // detail match
    @Test
    fun detailEvent() {
        val event: MutableList<Event> = mutableListOf()
        val response = EventResponse(event)
        val id = 133604
        GlobalScope.launch {
            Mockito.`when`(
                service.sportdbApi.getDetailEvent(id)
                    .test()
                    .assertSubscribed()
                    .assertValue(response)
                    .assertComplete()
                    .assertNoErrors()
            )
            presenter.detailEvent(id)

            Mockito.verify(presenter).getDetailEvent()
            Mockito.verify(view).loadDetailEventSuccess()
        }
    }
}