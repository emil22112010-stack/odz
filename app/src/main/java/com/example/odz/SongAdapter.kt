package com.example.odz

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SongAdapter(private val context: Context, private val songs: List<Song>) :
    RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    class SongViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvSongTitle)
        val tvArtist: TextView = view.findViewById(R.id.tvSongArtist)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songs[position]
        holder.tvTitle.text = song.id
        holder.tvArtist.text = song.statement

        holder.itemView.setOnClickListener {
            val intent = Intent(context, SongDetailActivity::class.java)
            intent.putExtra("TITLE", song.id)
            intent.putExtra("ARTIST", song.statement)
            intent.putExtra("TEXT", song.steps)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = songs.size
    }
