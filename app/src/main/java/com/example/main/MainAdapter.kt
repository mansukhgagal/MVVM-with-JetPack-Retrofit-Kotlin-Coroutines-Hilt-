package com.example.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.R
import com.example.databinding.ItemEmptyBinding
import com.example.databinding.ItemProdListBinding
import com.example.imageloader.GlideApp
import com.example.model.ApiUser
import com.example.utils.EmptyVH

class MainAdapter(val context: Context, private val dataList: List<Any>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    companion object {
        const val VT_USER_LIST = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            VT_USER_LIST -> {
                val binding = ItemProdListBinding.inflate(inflater, parent, false)
                return ProductListVH(binding)
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
            is ApiUser -> VT_USER_LIST
            else -> -1
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = dataList[position]
        if (holder is ProductListVH && data is ApiUser) {
            holder.bind(data)
        } else {
            //not possible
        }
    }

    inner class ProductListVH(private val binding: ItemProdListBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            binding.itemRoot.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if (v?.id == R.id.item_root) {
//                val intent = Intent(context, ProductDetailsActivity::class.java)
//                context.startActivity(intent)
            }
//            v?.findNavController()?.navigate(R.id.action_homeFragment_to_categoryFragment)
        }

        fun bind(data: ApiUser) {

            binding.textProdName.text = data.name
            binding.textCatName.text = data.email
            binding.textPrice.text = data.id.toString()
            binding.textIngradient.text = data.name

            GlideApp.with(binding.imageCat.context)
                .load(data.avatar)
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(8)))
                .into(binding.imageCat)
        }

    }

}