package com.test.testapp1202.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.test.testapp1202.R
import com.test.testapp1202.helper.DateToString
import com.test.testapp1202.helper.ImageDownloader
import com.test.testapp1202.model.Film
import java.net.URL
import java.util.*


class FilmsAdapter(var items: List<Film>) : RecyclerView.Adapter<FilmsAdapter.MainHolder>() {

    var onItemClick: ((Int) -> Unit)? = null
    var imageDownloader: ImageDownloader = ImageDownloader()
    var imageDownloaderStop: ((ImageDownloader) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MainHolder(LayoutInflater.from(parent.context).inflate(R.layout.film_item, parent, false))

    override fun getItemCount() = items.size
    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.bind(items[position], position)
    }

    inner class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val filmName = itemView.findViewById<TextView>(R.id.name_tv)
        private val filmDate = itemView.findViewById<TextView>(R.id.date_tv)
        private val filmImage = itemView.findViewById<ImageView>(R.id.image_iv)

        fun bind(item: Film, index: Int) {
            filmName.text = item.name
            filmDate.text = DateToString().dateToString(Date(item.time))
            filmImage.setImageResource(android.R.color.transparent)
            imageDownloader.setImageToImageView(filmImage, URL(item.image))

            imageDownloaderStop?.let { it(imageDownloader) }

            itemView.setOnClickListener {
                onItemClick?.invoke(index)
            }
        }
    }

    fun setFilms(items: List<Film>) {
        if (items.isNotEmpty()) {
            this.items = items
        }
        notifyDataSetChanged()
    }
}