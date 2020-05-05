package com.test.testapp1202.view

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.textclassifier.SelectionEvent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.test.testapp1202.R
import com.test.testapp1202.viewmodel.MainViewModel


class SlideFragment : Fragment() {

    private var myContext: FragmentActivity? = null
    private lateinit var viewModel: MainViewModel


    override fun onAttach(activity: Activity) {
        myContext = activity as FragmentActivity
        super.onAttach(activity)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_slider, container, false)
    }

    var mPager: ViewPager? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPager = view.findViewById(R.id.pager)
        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        }!!

        if (viewModel.getIsUseDatabase()) {
            viewModel.getFilms().observe(viewLifecycleOwner, Observer {
                val pagerAdapter =
                    ScreenSlidePagerAdapter(myContext!!.supportFragmentManager, it.size)
                mPager!!.adapter = pagerAdapter
                mPager!!.currentItem = viewModel.getSelecteditem()
            })
        } else {
            val pagerAdapter = ScreenSlidePagerAdapter(
                myContext!!.supportFragmentManager,
                viewModel.getFilmsWithoutDB().size
            )
            mPager!!.adapter = pagerAdapter
            mPager!!.currentItem = viewModel.getSelecteditem()
        }


    }


    private inner class ScreenSlidePagerAdapter(fm: FragmentManager, var size: Int) :
        FragmentStatePagerAdapter(
            fm,
            FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        ) {
        override fun getItem(position: Int): Fragment {
            val sif = SelectedItemFragment()
            sif.setIndexFilm(position)
            return sif
        }

        override fun getCount(): Int {
            return size
        }
    }


}