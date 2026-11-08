package com.example.odz

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import java.io.InputStreamReader

class SongListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)

        // Кнопка "Назад"
        findViewById<TextView>(R.id.btnBack).setOnClickListener { finish() }

        // Загрузка базы из JSON
        var catalog: Catalog? = null
        try {
            assets.open("textbooks.json").use { stream ->
                InputStreamReader(stream, "UTF-8").use { reader ->
                    catalog = Gson().fromJson(reader, Catalog::class.java)
                }
            }
        } catch (e: Exception) { 
            return 
        }

        // Достаем все песни
        val allSongs = catalog?.textbooks?.flatMap { it.problems } ?: emptyList()

        // Настраиваем список
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SongAdapter(this, allSongs)
    }
}
