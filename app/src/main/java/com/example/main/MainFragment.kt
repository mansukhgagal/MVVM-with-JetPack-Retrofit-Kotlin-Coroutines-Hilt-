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
import androidx.recyclerview.widget.RecyclerView
import com.example.R
import com.example.imageloader.GlideApp
import com.example.imageloader.GlideRequests
import com.example.network.Status
import com.example.utils.AndroidLifecycleUtils
import com.example.utils.EqualSpacingItemDecoration
import com.example.utils.Utils
import kotlinx.android.synthetic.main.fragment_main.*
import timber.log.Timber
import kotlin.math.abs

class MainFragment : Fragment() {

    private var adapter: MainAdapter? = null
    private val dataList: MutableList<Any> = mutableListOf()
    private lateinit var mGlideRequestManager:GlideRequests
    private val viewModel:MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

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

        mGlideRequestManager = GlideApp.with(this)

        recycler_view.layoutManager = LinearLayoutManager(requireContext())
        recycler_view.addItemDecoration(EqualSpacingItemDecoration(2))
        adapter = MainAdapter(requireActivity(), dataList,mGlideRequestManager)
        recycler_view.adapter = adapter

        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (abs(dy) > SCROLL_THRESHOLD) {
                    mGlideRequestManager.pauseRequests()
                } else {
                    resumeRequestsIfNotDestroyed()
                }
            }
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    resumeRequestsIfNotDestroyed()
                }
            }
        })

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
                    progress_bar.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    progress_bar.visibility = View.GONE
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
                    if(!Utils.isInternetAvailable(requireContext())) {
                        text_error.text = getString(R.string.message_internet)
                    } else {
                        text_error.text = it.message
                        Timber.d("ERROR : ${it.message}")
                    }
                }
            }
        })
    }

    private fun resumeRequestsIfNotDestroyed() {
        if (!AndroidLifecycleUtils.canLoadImage(this)) {
            return
        }
        mGlideRequestManager.resumeRequests()
    }


    companion object {
        private const val SCROLL_THRESHOLD = 30
        private const val TAG = "MainFragment"
    }
}