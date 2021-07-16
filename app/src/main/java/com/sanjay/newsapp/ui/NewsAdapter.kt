package com.sanjay.newsapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sanjay.newsapp.R
import com.sanjay.newsapp.data.model.Articles
import kotlinx.android.synthetic.main.list_item.view.*

class NewsAdapter(
    private val context: Context,
    private val articleList: List<Articles>
) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private var itemList: List<Articles>

    init {
        itemList = articleList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item, parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(itemList[position])

    fun setFilter(model: List<Articles>?) {
        itemList = ArrayList()
        if (model != null) {
            (itemList as ArrayList<Articles>).addAll(model)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return itemList?.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(model: Articles) {
            Glide.with(context).load(model.urlToImage)
                .into(itemView.imageView)
            itemView.tv_title.text = model.title
            itemView.tv_desc.text = model.description
            itemView.tv_source.text = context.getString(R.string.source, model.source.name)
            itemView.tv_publish_at.text = model.publishedAt
        }
    }
}