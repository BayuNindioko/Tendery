package com.example.tendery.ui.preTest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tendery.MainActivity
import com.example.tendery.databinding.ActivityPreTestBinding
import com.example.tendery.ui.preTest.rv_test.Quiz
import com.example.tendery.ui.preTest.rv_test.TestActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PreTestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPreTestBinding
    private lateinit var fStore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    lateinit var quiz: Quiz
    var quizzes : MutableList<Quiz>? = null
    var questions: MutableMap<String, Soal>? = null
    var index = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Pre Test & Post Test"
        }

        binding = ActivityPreTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpFirestore()
        setUpEventListener()
    }

    private fun setUpEventListener() {
        binding.btnPrevious.setOnClickListener {
            index--
            bindViews()
        }

        binding.btnNext.setOnClickListener {
            index++
            bindViews()
        }

        binding.btnSubmit.setOnClickListener {
            calculateScore()

        }
    }


    private fun setUpFirestore() {
        val firestore = FirebaseFirestore.getInstance()
        var date = intent.getStringExtra("DATE")
        if (date != null) {
            firestore.collection("Test").whereEqualTo("title", date)
                .get()
                .addOnSuccessListener {
                    if(it != null && !it.isEmpty){
                        quizzes = it.toObjects(Quiz::class.java)
                        questions = quizzes!![0].list_soal
                        Log.d("question1", questions.toString())
                        bindViews()
                    }
                }
        }
    }

    private fun bindViews() {
        binding.btnPrevious.visibility = View.GONE
        binding.btnSubmit.visibility = View.GONE
        binding.btnNext.visibility = View.GONE

        if(index == 1){
            binding.btnNext.visibility = View.VISIBLE
        }
        else if(index == questions!!.size) {
            binding.btnSubmit.visibility = View.VISIBLE
            binding. btnPrevious.visibility = View.VISIBLE
        }
        else{
            binding.btnPrevious.visibility = View.VISIBLE
            binding.btnNext.visibility = View.VISIBLE
        }

        val question = questions!!["soal$index"]
        Log.d("question", question.toString())
        question?.let {
            binding.description.text = it.soal
            val optionAdapter = SoalAdapter(this, it)
            binding.optionList.layoutManager = LinearLayoutManager(this)
            binding.optionList.adapter = optionAdapter
            binding.optionList.setHasFixedSize(true)
        }
    }
    private fun calculateScore() {
        var score = 0

        for (entry in questions!!.entries) {
            val question = entry.value

            if (question.jawaban == question.userAnswer) {
                score += 10
            }
        }
        submitNilai(score)
    }

    private fun submitNilai(nilai: Int) {
        auth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        val currentUser = auth.currentUser
        binding.progressBar7.visibility = View.VISIBLE
        auth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        binding.progressBar7.visibility = View.VISIBLE

        val uid = currentUser?.uid

        if (uid != null) {
            fStore.collection("Users").document(uid)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.contains("PreTest")) {
                        val preTestValue = documentSnapshot.getLong("PreTest")
                        if (preTestValue != null) {
                            if (preTestValue == 0L) {
                                val userDocRef = fStore.collection("Users").document(uid)

                                userDocRef.update("PreTest", nilai)
                                    .addOnSuccessListener {
                                        val intent = Intent(this, NilaiPreTestActivity::class.java)
                                        intent.putExtra("nilaiPreTest", nilai.toString())
                                        startActivity(intent)
                                        finish()
                                        binding.progressBar7.visibility = View.GONE
                                    }
                                    .addOnFailureListener { e ->
                                        Toast.makeText(this, "Coba Lagi", Toast.LENGTH_SHORT).show()
                                        binding.progressBar7.visibility = View.GONE
                                    }
                            } else {
                                val userDocRef = fStore.collection("Users").document(uid)

                                userDocRef.update("PostTest", nilai)
                                    .addOnSuccessListener {
                                        val intent = Intent(this, NilaiPreTestActivity::class.java)
                                        intent.putExtra("nilaiPreTest", nilai.toString())
                                        startActivity(intent)
                                        finish()
                                        binding.progressBar7.visibility = View.GONE
                                    }
                                    .addOnFailureListener { e ->
                                        Toast.makeText(this, "Coba Lagi", Toast.LENGTH_SHORT).show()
                                        binding.progressBar7.visibility = View.GONE
                                    }
                            }
                        } else {
                            Toast.makeText(baseContext, "Data nilai PreTest tidak valid", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(baseContext, "Belum ada nilai PreTest", Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }

}