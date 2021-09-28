package com.example.news

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.databinding.ItemEmptyBinding
import com.example.databinding.ItemHeadlineBinding
import com.example.imageloader.GlideRequests
import com.example.model.NewsArticle
import com.example.utils.EmptyVH

class NewsHeadlineAdapter(val context: Context,
                          private val dataList: List<Any>,
                          val glideRequest: GlideRequests) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    companion object {
        const val VT_ARTICLE_LIST = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            VT_ARTICLE_LIST -> {
                val binding = ItemHeadlineBinding.inflate(inflater, parent, false)
                return HeadlineVH(binding,this)
            }
            else -> {
                val binding = ItemEmptyBinding.inflate(inflater, parent, false)
                return EmptyVH(binding)
            }
        }
    }

    override fun getItemCount(): Int = dataList.size

    fun getItem(position: Int): Any? {
        if (position != RecyclerView.NO_POSITION) {
            return dataList[position]
        }
        return null
    }

    override fun getItemViewType(position: Int): Int {
        val dataItem = getItem(position)
        return when (dataItem) {
            is NewsArticle -> VT_ARTICLE_LIST
            else -> -1
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = dataList[position]
        if (holder is HeadlineVH && data is NewsArticle) {
            holder.bind(data)
        }
    }
}