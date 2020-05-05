package com.test.testapp1202.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.test.testapp1202.R
import com.test.testapp1202.helper.DateToString
import com.test.testapp1202.helper.ImageDownloader
import com.test.testapp1202.model.Film
import com.test.testapp1202.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_selected_item.*
import java.net.URL
import java.util.*


class SelectedItemFragment : Fragment() {

    var index: Int = 0
    fun setIndexFilm(index: Int) {
        this.index = index
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retainInstance = false
        return inflater.inflate(R.layout.fragment_selected_item, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        }!!

        if (viewModel.getIsUseDatabase()) {
            viewModel.getFilms().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                fillViews(it[index])
            })
        } else {
            fillViews(viewModel.getFilmsWithoutDB()[index])
        }

    }

    fun fillViews(film: Film) {
        ImageDownloader().setImageToImageView(iv_image, URL(film.image))
        tv_name.text = film.name
        tv_description.text = film.description
        tv_date.text = DateToString().dateToString(Date(film.time))
    }

}