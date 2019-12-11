package kade.submission2.footballmatchschedule.presenter

import kade.submission2.footballmatchschedule.contract.DetailLeagueContract
import kade.submission2.footballmatchschedule.model.DetailLeague
import kade.submission2.footballmatchschedule.model.DetailLeagueResponse
import kade.submission2.footballmatchschedule.service.ApiFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class DetailLeaguePresenterTest {

    @Mock
    lateinit var presenter: DetailLeaguePresenter

    @Mock
    lateinit var service: ApiFactory

    @Mock
    lateinit var view: DetailLeagueContract.View

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailLeaguePresenter(view)
    }

    // detail league
    @Test
    fun detailLeague() {
        val league: MutableList<DetailLeague> = mutableListOf()
        val response = DetailLeagueResponse(league)
        val id = 133604
        GlobalScope.launch {
            `when`(service.sportdbApi.getDetailLeague(id)
                .test()
                .assertSubscribed()
                .assertValue(response)
                .assertComplete()
                .assertNoErrors()
            )
            presenter.detailLeague(id)

            verify(presenter).getDetailLeague()
            verify(view).loadDeatilLeagueSuccess()
        }
    }
}