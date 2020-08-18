package com.test.uwork.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.test.uwork.R
import com.test.uwork.models.Picture
import com.test.uwork.models.State
import kotlinx.android.synthetic.main.pictures_item.view.*
import kotlinx.android.synthetic.main.progress_layout.view.*


class PicturesAdapter : PagedListAdapter<Picture, RecyclerView.ViewHolder>(PICTURE_COMPARATOR) {

    private val DATA_VIEW_TYPE = 1
    private val FOOTER_VIEW_TYPE = 2

    private var state = State.LOADING

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == DATA_VIEW_TYPE) ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.pictures_item, parent, false)
        )
        else FooterViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.progress_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_VIEW_TYPE) {
            val picture = getItem(position)
            picture?.let {
                (holder as ViewHolder).bindData(it)
            }
        } else {
            (holder as FooterViewHolder).bindData(state)
        }
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image = itemView.picture
        private val author = itemView.txtAuthor


        fun bindData(picture: Picture) {
            author.text = picture.author
            Picasso.get().load(picture.photo).error(R.drawable.ic_baseline_image_24).fit()
                .centerCrop().into(image, object : Callback {
                override fun onSuccess() {
                    Log.i("picassolog","success")
                }

                override fun onError(e: Exception?) {
                    Log.i("picassolog","fail:${e?.message}")
                }

            })

        }
    }


    inner class FooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val progressBar = itemView.progressBar

        fun bindData(status: State?) {
            progressBar.visibility = if (status == State.LOADING) View.VISIBLE else View.GONE
        }

    }


    companion object {
        private val PICTURE_COMPARATOR = object : DiffUtil.ItemCallback<Picture>() {
            override fun areItemsTheSame(oldItem: Picture, newItem: Picture): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Picture, newItem: Picture): Boolean {
                return newItem == oldItem
            }

        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) 1 else 0
    }

    private fun hasFooter(): Boolean {
        return super.getItemCount() != 0 && (state == State.LOADING || state == State.ERROR)
    }

    fun setState(state: State) {
        this.state = state
        notifyItemChanged(super.getItemCount())
    }


}






