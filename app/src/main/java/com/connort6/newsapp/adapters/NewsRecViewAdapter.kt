package com.connort6.newsapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.connort6.newsapp.data.Article
import com.connort6.newsapp.databinding.ItemBreakingNewsBinding
import com.connort6.newsapp.databinding.ItemViewMoreRecViewBinding

class NewsRecViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onItemClick: ((Article) -> Unit)? = null

    inner class NewsViewHolder(val newsBinding: ItemBreakingNewsBinding) :
        RecyclerView.ViewHolder(newsBinding.root) {
        init {
            newsBinding.root.setOnClickListener {
                onItemClick?.invoke(differ.currentList[adapterPosition])
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {


        val binding =
            ItemBreakingNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)

    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val holder1 = holder as NewsViewHolder
            val article = differ.currentList[position]
            val imView = holder1.newsBinding.imvArticle
            val imgUrl = article.urlToImage
            Glide.with(holder1.newsBinding.root)
                .load(imgUrl)
                .centerCrop()
                .into(imView)
            if (article.author != null) {
                holder1.newsBinding.tvAuthor.text = "by " + article.author
            }
            holder1.newsBinding.tvTopic.text = article.title

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

}