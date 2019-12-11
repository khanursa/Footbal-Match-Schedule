package kade.submission2.footballmatchschedule.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kade.submission2.footballmatchschedule.R
import kade.submission2.footballmatchschedule.adapter.NextPastScheduleAdapter
import kade.submission2.footballmatchschedule.model.Event
import kade.submission2.footballmatchschedule.view.DetailEventActivity
import kotlinx.android.synthetic.main.fragment_event.view.*
import kotlin.properties.Delegates
import org.jetbrains.anko.support.v4.startActivity

class EventScheduleFragment : Fragment(), NextPastScheduleAdapter.ClickItem {
    private val tags: String = "NEXTSCHEDULE"
    private lateinit var root: View
    private var adapterSchedule by Delegates.notNull<NextPastScheduleAdapter>()

    fun newInstance(nextEvent: ArrayList<Event>): EventScheduleFragment {
        val schedule = EventScheduleFragment()
        val bundle = Bundle()

        bundle.putParcelableArrayList("nextSchedule", nextEvent)
        schedule.arguments = bundle

        Log.d(tags, "${nextEvent.size}")

        return schedule
    }

    override fun onClickItem(id: Int) {
        startActivity<DetailEventActivity>("idEvent" to id)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_event, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        root.recyclerNextEvent.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        val nextSchedule: ArrayList<Event>? = arguments?.getParcelableArrayList("nextSchedule")

        nextSchedule?.let {
            adapterSchedule = NextPastScheduleAdapter(it)
            adapterSchedule.setOnClickListener(this)
            root.recyclerNextEvent.adapter = adapterSchedule
        }
    }
}
