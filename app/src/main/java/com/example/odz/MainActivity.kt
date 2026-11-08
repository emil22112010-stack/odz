package com.example.odz

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Находим нашу большую кнопку Гитары
        val btnGuitar = findViewById<LinearLayout>(R.id.btnGuitar)

        // При нажатии открываем экран со списком песен
        btnGuitar.setOnClickListener {
            startActivity(Intent(this, SongListActivity::class.java))
        }
    }
}
