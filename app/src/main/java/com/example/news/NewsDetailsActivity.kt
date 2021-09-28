package com.example.news

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.R
import com.example.databinding.ActivityNewsDetailsBinding
import com.example.imageloader.GlideApp
import com.example.model.NewsArticle
import com.example.utils.*
import com.example.utils.Constant.Companion.KEY_NEWS_DATA

class NewsDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initControls()
    }

    private fun initControls() {

        binding.topAppBar.setNavigationOnClickListener { finish() }

        binding.textLabelHeadline.setPadding(16,0,16,0)
        binding.textLabelHeadline.background = getRectShape(R.color.color_headline_strip,8f)

        val data: NewsArticle? = intent.getParcelableExtra(KEY_NEWS_DATA)
        data?.apply {

            val placeHolder = ColorDrawable(ContextCompat.getColor(this@NewsDetailsActivity, R.color.color_placeholder))

            GlideApp.with(this@NewsDetailsActivity)
                .load(urlToImage)
                .placeholder(placeHolder)
                .error(placeHolder)
                .override(convertDpToPixel(50f))
                .into(binding.imageAuthor)

            binding.textAuthor.text = author ?: source?.name ?: "--"

            binding.textTitle.text = title
            binding.textDate.text = publishedAt
            binding.textDescription.text = description

            //Caching applied in Glide Module
            GlideApp.with(this@NewsDetailsActivity)
                .load(urlToImage)
                .placeholder(placeHolder)
                .error(placeHolder)
                .into(binding.imageHeadline)

            binding.imageHeadline.setOnClickListener {
                launchUrl(url)
            }
        }


    }

}