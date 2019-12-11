package kade.submission2.footballmatchschedule.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kade.submission2.footballmatchschedule.R
import kade.submission2.footballmatchschedule.helper.MappingImage
import kotlinx.android.synthetic.main.holder_fanart_league.view.*

/**
 *  Created by Muhammad Rahamatul Khair 0n 2019-11-10
 */

class FanartFragment: Fragment() {
    private lateinit var root: View
    private var mappingImage: MappingImage = MappingImage()

    fun newInstance(image: String?): FanartFragment {
        val bundle = Bundle()
            bundle.putString("getImage", image)
        val fanart = FanartFragment()
            fanart.arguments = bundle
        return fanart
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.holder_fanart_league, container, false)
        arguments?.getString("getImage")?.let {
            mappingImage.placingImage(
                requireContext(),
                it,
                root.holderImage)
        }
//        Log.d("FanartFragment", arguments?.getString("getImage"))

        return root
    }
}