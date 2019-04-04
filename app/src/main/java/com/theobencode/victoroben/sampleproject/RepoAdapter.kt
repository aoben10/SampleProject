package com.theobencode.victoroben.sampleproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.theobencode.victoroben.sampleproject.network.Repo
import kotlinx.android.synthetic.main.list_item_repo.view.repoId
import kotlinx.android.synthetic.main.list_item_repo.view.repoName
import kotlinx.android.synthetic.main.list_item_repo.view.stars

class RepoAdapter(private val repoItemClickBlock: (String) -> Unit) : ListAdapter<Repo, RepoAdapter.RepoViewHolder>(RepoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val viewHolder = RepoViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_repo, parent, false))

        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                repoItemClickBlock(getItem(position).url)
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repo = getItem(position)
        holder.itemView.apply {
            repoName.text = repo.name
            repoId.text = context.getString(R.string.repo_id, repo.id)
            stars.text = context.getString(R.string.number_of_stars, repo.stargazersCount)
        }
    }

    class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}

private class RepoDiffCallback : DiffUtil.ItemCallback<Repo>() {

    override fun areItemsTheSame(oldItem: Repo, newItem: Repo) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Repo, newItem: Repo) = oldItem == newItem
}