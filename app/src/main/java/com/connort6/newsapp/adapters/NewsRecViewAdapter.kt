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

    var allShowing: Boolean = false
    var onItemClick: ((Article) -> Unit)? = null
    var viewAllClick: ((Boolean) -> Unit)? = null

    inner class NewsViewHolder(val newsBinding: ItemBreakingNewsBinding) :
        RecyclerView.ViewHolder(newsBinding.root) {
        init {
            newsBinding.root.setOnClickListener {
                onItemClick?.invoke(differ.currentList[adapterPosition])
            }
        }
    }

    inner class ViewAllViewHolder(val viewAllBinding: ItemViewMoreRecViewBinding) :
        RecyclerView.ViewHolder(viewAllBinding.root) {
        init {
            viewAllBinding.root.setOnClickListener {
                viewAllClick?.invoke(true)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == 2) {
            val binding = ItemViewMoreRecViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ViewAllViewHolder(binding)
        }

        val binding =
            ItemBreakingNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)

    }

    override fun getItemViewType(position: Int): Int {
        if (!allShowing && position == differ.currentList.size) {
            return 2
        }
        return 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position < differ.currentList.size) {
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
        } else if (!allShowing) {
            val holder1 = holder as ViewAllViewHolder

        }
    }

    override fun getItemCount(): Int {
        if (differ.currentList.size > 0) {
            return differ.currentList.size + 1
        }

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