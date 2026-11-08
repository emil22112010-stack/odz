package com.example.odz

data class Song(val id: String, val statement: String?, val answer: String, val steps: String)
data class Textbook(val grade: String, val title: String, val author: String, val problems: List<Song>)
data class Catalog(val textbooks: List<Textbook>)
