package com.example.odz

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import java.io.InputStreamReader

data class Problem(val id: String, val statement: String?, val answer: String, val steps: String)
data class Textbook(val grade: String, val title: String, val author: String, val problems: List<Problem>)
data class Catalog(val textbooks: List<Textbook>)

class MainActivity : AppCompatActivity() {
    private lateinit var catalog: Catalog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinnerGrade = findViewById<Spinner>(R.id.spinnerGrade)
        val spinnerBook = findViewById<Spinner>(R.id.spinnerBook)
        val editProblem = findViewById<EditText>(R.id.editProblemId)
        val btnShow = findViewById<Button>(R.id.btnShow)
        val tvResult = findViewById<TextView>(R.id.tvResult)

        try {
            assets.open("textbooks.json").use { stream ->
                InputStreamReader(stream, "UTF-8").use { reader ->
                    catalog = Gson().fromJson(reader, Catalog::class.java)
                }
            }
        } catch (e: Exception) {
            tvResult.text = "Ошибка загрузки базы: ${e.message}"
            return
        }

        val grades = catalog.textbooks.map { it.grade }.distinct()
        if (grades.isEmpty()) {
            Toast.makeText(this, "База пуста", Toast.LENGTH_LONG).show()
            return
        }
        
        spinnerGrade.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, grades).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        fun refreshBooks() {
            val selectedGrade = spinnerGrade.selectedItem as String
            val books = catalog.textbooks.filter { it.grade == selectedGrade }
                .map { "${it.title} — ${it.author}" }
            spinnerBook.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, books).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
        }

        spinnerGrade.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) { refreshBooks() }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        btnShow.setOnClickListener {
            val grade = spinnerGrade.selectedItem as String
            val bookIndex = spinnerBook.selectedItemPosition
            val booksFiltered = catalog.textbooks.filter { it.grade == grade }
            
            if (bookIndex < 0 || bookIndex >= booksFiltered.size) {
                tvResult.text = "Выберите учебник."
                return@setOnClickListener
            }
            
            val book = booksFiltered[bookIndex]
            val pid = editProblem.text.toString().trim()
            val found = book.problems.find { it.id == pid }
            
            if (found != null) {
                tvResult.text = "Задача: ${found.id}\n\nУсловие: ${found.statement ?: "—"}\n\nОтвет: ${found.answer}\n\nРешение:\n${found.steps}"
            } else {
                tvResult.text = "Задача \"$pid\" не найдена в этом учебнике."
            }
        }

        spinnerGrade.setSelection(0)
    }
}
