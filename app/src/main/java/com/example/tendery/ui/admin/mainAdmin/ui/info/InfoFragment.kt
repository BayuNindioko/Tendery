package com.example.tendery.ui.admin.mainAdmin.ui.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tendery.databinding.FragmentNotificationsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class InfoFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val user = FirebaseAuth.getInstance().currentUser
        val userId = user?.uid

        if (userId != null) {
            val firestore = FirebaseFirestore.getInstance()
            val userCollection = firestore.collection("Users")

            userCollection.get()
                .addOnSuccessListener { querySnapshot ->
                    val userList = mutableListOf<User>()

                    for (document in querySnapshot.documents) {
                        val user = document.toObject(User::class.java)
                        if (user != null) {
                            userList.add(user)
                        }
                    }

                    val recyclerView = binding.rvUser
                    val userAdapter = InfoAdapter(userList) { user ->

                    }
                    recyclerView.adapter = userAdapter
                    recyclerView.layoutManager = LinearLayoutManager(requireContext())
                }
                .addOnFailureListener { exception ->

                }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}