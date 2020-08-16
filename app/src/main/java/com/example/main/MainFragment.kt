package com.example.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.R
import com.example.network.Status
import com.example.utils.EqualSpacingItemDecoration
import com.example.utils.Utils
import kotlinx.android.synthetic.main.fragment_main.*
import timber.log.Timber

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private var adapter: MainAdapter? = null
    private val dataList: MutableList<Any> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.tag(TAG)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recycler_view.layoutManager = LinearLayoutManager(requireContext())
        recycler_view.addItemDecoration(EqualSpacingItemDecoration(2))
        adapter = MainAdapter(requireActivity(), dataList)
        recycler_view.adapter = adapter

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        subscribeUI()
        viewModel.fetch()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        topAppBar.setNavigationOnClickListener {
            requireActivity().finish()
        }
    }

    private fun subscribeUI() {
        viewModel.userList.observe(viewLifecycleOwner, Observer {
            text_error.visibility = GONE
            when (it.status) {
                Status.LOADING -> {
                    Timber.d("LOADING")
                    progress_bar.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    progress_bar.visibility = View.GONE
                    Timber.d("SUCCESS")
                    val start = dataList.size
                    it.data?.let { it1 ->
                        val count = it1.size
                        dataList.addAll(it1)
                        adapter?.notifyItemRangeInserted(start,count)
                    }
                }
                Status.ERROR -> {
                    progress_bar.visibility = View.GONE
                    text_error.visibility = View.VISIBLE
                    Timber.d("ERROR : ${it.message}")
                    if(!Utils.isInternetAvailable(requireContext())) {
                        text_error.text = "Check your connection!"
                    } else {
                        text_error.text = it.message
                        Timber.d("ERROR : ${it.message}")
                    }
                }
            }
        })
    }

    companion object {
        private const val TAG = "MainFragment"
    }
}