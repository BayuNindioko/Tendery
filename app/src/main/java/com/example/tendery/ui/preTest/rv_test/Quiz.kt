package com.example.tendery.ui.preTest.rv_test

import com.example.tendery.ui.preTest.Soal

data class Quiz (
    var id : String = "",
    var title: String = "",
    var list_soal: MutableMap<String, Soal> = mutableMapOf()
)
