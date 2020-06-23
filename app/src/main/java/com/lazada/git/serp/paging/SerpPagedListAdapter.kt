package com.lazada.git.serp.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lazada.git.R
import com.lazada.git.RepoListQuery
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_content_repo.view.*


class SerpPagedListAdapter constructor(private val picasso: Picasso) :
    PagedListAdapter<RepoListQuery.Edge, SerpPagedListAdapter.ViewHolder>(
        DIFF_CALLBACK
    ) {
    private val layout = R.layout.card_view_repo_details

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ViewHolder(view, picasso)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val repository = getItem(position)!!.node!!.asRepository
        if (repository != null) {
            holder.setItem(repository)
        }
    }

    inner class ViewHolder(itemView: View, private val picasso: Picasso) :
        RecyclerView.ViewHolder(itemView) {
        fun setItem(item: RepoListQuery.AsRepository?) {
            item?.let { repo ->
                (repo.owner.avatarUrl as? String)?.let { avatar ->
                    picasso.load(avatar).into(itemView.cardViewImage)
                }
                itemView.cardViewTitle.text = repo.name
                itemView.cardViewDescription.text = repo.description
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<RepoListQuery.Edge> =
            object : DiffUtil.ItemCallback<RepoListQuery.Edge>() {
                override fun areItemsTheSame(
                    oldRepo: RepoListQuery.Edge,
                    newRepo: RepoListQuery.Edge
                ): Boolean {
                    return oldRepo.node!!.asRepository!!.name === newRepo.node!!.asRepository!!.name
                }

                override fun areContentsTheSame(
                    oldRepo: RepoListQuery.Edge,
                    newRepo: RepoListQuery.Edge
                ): Boolean {
                    return oldRepo == newRepo
                }
            }
    }
}


