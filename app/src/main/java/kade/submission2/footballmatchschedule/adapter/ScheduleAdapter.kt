package kade.submission2.footballmatchschedule.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import kade.submission2.footballmatchschedule.R
import kade.submission2.footballmatchschedule.model.Event
import kade.submission2.footballmatchschedule.view.fragment.EventScheduleFragment
import kotlin.collections.ArrayList
import kade.submission2.footballmatchschedule.adapter.TAB_TITLES as TAB_TITLES1

private val TAB_TITLES = arrayOf(
    R.string.tabPast, R.string.tabNext
)

/**
 *  Created by Muhammad Rahamatul Khair 0n 2019-11-19
 */

class ScheduleAdapter(private val context: Context, fm: FragmentManager, private var totalTabs: Int, private val nextEvent: ArrayList<Event>, private val pastEvent: ArrayList<Event>): FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        val fragment: Fragment = when(position){
            0 -> EventScheduleFragment().newInstance(
                pastEvent
            )
            1 -> EventScheduleFragment().newInstance(
                nextEvent
            )
            else -> EventScheduleFragment().newInstance(
                pastEvent

            )
        }
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(
            TAB_TITLES1[position]
        )
    }

    override fun getCount(): Int {
        return totalTabs
    }
}