package com.example.odz

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SongDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_detail)

        // Кнопка "Назад"
        findViewById<TextView>(R.id.btnBackDetail).setOnClickListener { finish() }

        // Получаем данные из списка
        val title = intent.getStringExtra("TITLE") ?: ""
        val artist = intent.getStringExtra("ARTIST") ?: ""
        val text = intent.getStringExtra("TEXT") ?: ""

        // Устанавливаем текст на экран
        findViewById<TextView>(R.id.tvDetailTitle).text = title
        findViewById<TextView>(R.id.tvDetailArtist).text = artist
        findViewById<TextView>(R.id.tvChordsText).text = text
        
        // Меняем заголовок в самом верху
        findViewById<TextView>(R.id.tvHeaderTitle).text = title
    }
}
