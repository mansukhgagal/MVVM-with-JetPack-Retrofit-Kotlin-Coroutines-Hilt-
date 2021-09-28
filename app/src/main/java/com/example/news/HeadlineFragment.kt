package com.example.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.R
import com.example.base.BaseFragment
import com.example.databinding.BottomsheetCountryBinding
import com.example.databinding.FragmentHeadlineBinding
import com.example.imageloader.GlideApp
import com.example.imageloader.GlideRequests
import com.example.model.NewsArticle
import com.example.utils.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import kotlin.math.abs

@AndroidEntryPoint
class HeadlineFragment : BaseFragment() {

    private var _binding: FragmentHeadlineBinding? = null
    private val binding get() = _binding!!
    private lateinit var mGlideRequestManager: GlideRequests
    private val viewModel: MainViewModel by viewModels()
    private lateinit var mAdapter: NewsHeadlineAdapter
    private val newsArticleList = mutableListOf<NewsArticle>()
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener
    private var countryFilter: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHeadlineBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mGlideRequestManager = GlideApp.with(this)

        binding.topAppBar.setNavigationOnClickListener { requireActivity().finish() }
        binding.topAppBar.setOnMenuItemClickListener {
            if (it.itemId == R.id.action_filter) {
                showBottomSheet()
            }
            return@setOnMenuItemClickListener false
        }

        scrollListener = object :
            EndlessRecyclerViewScrollListener(binding.recyclerView.layoutManager as StaggeredGridLayoutManager) {

            override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(view, dx, dy)
                if (abs(dy) > SCROLL_THRESHOLD) {
                    mGlideRequestManager.pauseRequests()
                } else {
                    resumeRequestsIfNotDestroyed()
                }
            }

            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                viewModel.fetchTopHeadlines(countryFilter)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    resumeRequestsIfNotDestroyed()
                }
            }
        }

        binding.recyclerView.addOnScrollListener(scrollListener)

        binding.recyclerView.addItemDecoration(GridSpacingItemDecoration(2, 32, true))
        mAdapter =
            NewsHeadlineAdapter(
                requireContext(),
                newsArticleList,
                glideRequest = mGlideRequestManager
            )
        binding.recyclerView.adapter = mAdapter
        viewModel.fetchTopHeadlines(countryFilter)
        subscribeUI()

        binding.swipeRefreshLayout.setOnRefreshListener {
            getNewsByCountry(countryFilter)
        }
    }

    private fun subscribeUI() {
        viewModel.topHeadlines.observe(viewLifecycleOwner, Observer {

            when (it) {
                is Resource.Loading -> {
                    showProgressBar()
                    binding.textError.hide()
                }
                is Resource.Success -> {
                    hideProgressBar()
                    val data = it.data?.article
                    data?.let {
                        Timber.d("dataSize ${data.size}")
                        val start = newsArticleList.size
                        val count = data.size
                        newsArticleList.addAll(data)
                        mAdapter.notifyItemRangeInserted(start, count)
                    }
                }
                is Resource.Error -> {
                    Timber.d("error")
                    hideProgressBar()

                    val errorMessage = if (Utils.isInternetAvailable(requireContext()))
                        getString(R.string.error_something_went_wrong)
                    else
                        getString(R.string.no_connection2)

                    if (newsArticleList.isNotEmpty()) {
                        SnackBarHelper.infoSnackBar(binding.root, errorMessage, getString(R.string.button_retry)) {
                            Timber.d("retry")
                            viewModel.fetchTopHeadlines(countryFilter)
                        }
                        scrollListener.resetState()
                    } else {
                        binding.textError.show()
                        binding.textError.text = errorMessage
                    }
                }
            }

        })
    }

    private fun showProgressBar() {
        if (!binding.swipeRefreshLayout.isRefreshing)
            if (newsArticleList.isEmpty())
                showProgress(binding.progressBar)
            else
                showProgress(binding.progressBarBottom)
    }

    private fun hideProgressBar() {
        if (newsArticleList.isEmpty())
            hideProgress(binding.progressBar)
        else
            hideProgress(binding.progressBarBottom)

        if (binding.swipeRefreshLayout.isRefreshing)
            binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun showBottomSheet() {
        val dialogView = BottomsheetCountryBinding.inflate(layoutInflater)
        val bottomSheetDialog = BottomSheetDialog(requireActivity())
        bottomSheetDialog.setContentView(dialogView.root)

        val clickListener = View.OnClickListener { v ->
            bottomSheetDialog.dismiss()
            when (v?.id) {
                R.id.option_in -> getNewsByCountry("in")
                R.id.option_usa -> getNewsByCountry("us")
                R.id.option_korea -> getNewsByCountry("kr")
                R.id.option_china -> getNewsByCountry("ch")
            }
        }

        dialogView.optionIn.setOnClickListener(clickListener)
        dialogView.optionUsa.setOnClickListener(clickListener)
        dialogView.optionKorea.setOnClickListener(clickListener)
        dialogView.optionChina.setOnClickListener(clickListener)

        bottomSheetDialog.show()
    }

    private fun getNewsByCountry(country: String?) {
        clearList()
        viewModel.fetchTopHeadlines(country)
    }

    private fun clearList() {
        mAdapter.notifyItemRangeRemoved(0, newsArticleList.size)
        newsArticleList.clear()
    }

    private fun resumeRequestsIfNotDestroyed() {
        if (!AndroidLifecycleUtils.canLoadImage(this)) {
            return
        }
        mGlideRequestManager.resumeRequests()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val SCROLL_THRESHOLD = 30
        private const val TAG = "HeadlineFragment"
    }
}