package com.connort6.newsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.connort6.newsapp.data.Article
import com.connort6.newsapp.databinding.ItemBreakingNewsBinding

class NewsRecViewAdapter(
    var articleList: List<Article> = ArrayList()
) : RecyclerView.Adapter<NewsRecViewAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(val binding: ItemBreakingNewsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding =
            ItemBreakingNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = articleList[position]
        val imView = holder.binding.imvArticle
        val imgUrl = article.urlToImage
        Glide.with(holder.binding.root).load(imgUrl).into(imView)
        if (article.author != null) {
            holder.binding.tvAuthor.text = "by " + article.author
        }else{
            holder.binding.tvAuthor.text = ""
        }
        holder.binding.tvTopic.text = article.title
    }

    override fun getItemCount(): Int {
        return articleList.size
    }


}