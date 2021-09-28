package com.example.news

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.R
import com.example.databinding.ItemHeadlineBinding
import com.example.model.NewsArticle
import com.example.utils.Constant.Companion.KEY_NEWS_DATA
import com.example.utils.getScreenHeight

class HeadlineVH(
    private val binding: ItemHeadlineBinding,
    private val adapter: NewsHeadlineAdapter
) :
    RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    val context = adapter.context
    val imageSize = context.getScreenHeight() / 2

    init {
        binding.card.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.card) {
            val data = adapter.getItem(adapterPosition)
            if(data is NewsArticle) {
                val detailIntent = Intent(context, NewsDetailsActivity::class.java)
                detailIntent.putExtra(KEY_NEWS_DATA, data)
                context.startActivity(detailIntent)
            }
        }
    }

    fun bind(data: NewsArticle) {

        binding.textTitle.text = data.title
        binding.textDate.text = data.publishedAt
        binding.textDescription.text = data.description

        val placeHolder = ColorDrawable(ContextCompat.getColor(context, R.color.color_placeholder))
        //Caching applied in Glide Module
        adapter.glideRequest
            .load(data.urlToImage)
            .override(imageSize)
            .placeholder(placeHolder)
            .error(placeHolder)
            .into(binding.imageHeadline)
    }

}