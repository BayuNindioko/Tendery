package com.example.tendery.ui.preTest.rv_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tendery.R
import com.example.tendery.databinding.ActivityPreTestBinding
import com.example.tendery.databinding.ActivityTestBinding
import com.google.firebase.firestore.FirebaseFirestore

class TestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestBinding
    lateinit var adapter: QuizAdapter
    private var quizList = mutableListOf<Quiz>()
    lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "PreTest dan PostTest"
        }
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpFireStore()
        setUpRecyclerView()
    }

    private fun setUpFireStore() {
        firestore = FirebaseFirestore.getInstance()
        val collectionReference = firestore.collection("Test")
        collectionReference.addSnapshotListener { value, error ->
            if(value == null || error != null){
                Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            Log.d("DATAPP", value.toObjects(Quiz::class.java).toString())
            quizList.clear()
            quizList.addAll(value.toObjects(Quiz::class.java))
            adapter.notifyDataSetChanged()
        }
    }

    private fun setUpRecyclerView() {
        adapter = QuizAdapter(this, quizList)
        binding.quizRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.quizRecyclerView.adapter = adapter
    }

}