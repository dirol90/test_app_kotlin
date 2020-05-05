package com.test.testapp1202.view

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.testapp1202.MainActivity
import com.test.testapp1202.R
import com.test.testapp1202.adapter.FilmsAdapter
import com.test.testapp1202.model.Film
import com.test.testapp1202.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private var adapter: FilmsAdapter? = null
    private var myContext: FragmentActivity? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retainInstance = true
        return inflater.inflate(
            R.layout.fragment_main, container,
            false
        )
    }

    override fun onAttach(activity: Activity) {
        myContext = activity as FragmentActivity
        super.onAttach(activity)
    }

    var recyclerView: RecyclerView? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        }!!

        val activity = activity as Context
        recyclerView = view.findViewById<RecyclerView>(R.id.main_rv)
        recyclerView!!.layoutManager = LinearLayoutManager(activity)

        reloadAdater()

        sort_btn.text = viewModel.getLastSortOrder()

        switch_db.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setIsUseDatabase(isChecked)
            if (isChecked) {
                recyclerView!!.removeAllViews()
                updateItemsFromLocalDatabaseWithApi()
            } else {
                recyclerView!!.removeAllViews()
                updateItemsDirectlyFromApi()
            }
        }

        switch_db.isChecked = viewModel.getIsUseDatabase()

        updateItemsDirectlyFromApi()
    }

    fun updateItemsFromLocalDatabaseWithApi() {
        reloadAdater()

        sort_btn.setOnClickListener {
            reloadAdater()
            when (sort_btn.text) {
                "SORT A-Z" -> {
                    viewModel.setLastSortOrder("SORT Z-A")
                }
                "SORT Z-A" -> {
                    viewModel.setLastSortOrder("SORT A-Z")
                }
            }

            viewModel.getFilms().observe(viewLifecycleOwner,
                Observer<List<Film>> { t ->
                    run {
                        adapter!!.setFilms(t!!); pb.visibility = View.GONE
                    }
                }); sort_btn.text = viewModel.getLastSortOrder()

        }

        viewModel.getFilms().observe(viewLifecycleOwner,
            Observer<List<Film>> { t ->
                run {
                    adapter!!.setFilms(t!!); pb.visibility = View.GONE
                }
            })
    }

    fun updateItemsDirectlyFromApi() {
        reloadAdater()

        sort_btn.setOnClickListener {
            reloadAdater()
            when (sort_btn.text) {
                "SORT A-Z" -> {
                    viewModel.setLastSortOrder("SORT Z-A")
                }
                "SORT Z-A" -> {
                    viewModel.setLastSortOrder("SORT A-Z")
                }
            }
            adapter!!.setFilms(viewModel.getFilmsWithoutDB()); pb.visibility =
            View.GONE; sort_btn.text = viewModel.getLastSortOrder()

        }

        adapter!!.setFilms(viewModel.getFilmsWithoutDB()); pb.visibility = View.GONE
    }

    fun reloadAdater() {
        if (adapter != null) {
            adapter!!.imageDownloaderStop = { i -> i.cancelDownload() }
        }
        adapter = FilmsAdapter(mutableListOf())
        recyclerView!!.adapter = adapter

        adapter!!.onItemClick = { index ->
            viewModel.setSelectedItem(index)
            (activity as MainActivity?)!!.startGaleryFragment()
        }
    }
}