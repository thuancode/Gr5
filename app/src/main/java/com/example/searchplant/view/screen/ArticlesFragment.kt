package com.example.searchplant.view.screen

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.searchplant.R
import com.example.searchplant.adapter.ArticlesAdapter
import com.example.searchplant.adapter.SpeciesAdapter
import com.example.searchplant.databinding.FragmentListSpeciesBinding
import com.example.searchplant.databinding.FragmentSpeciesBinding
import com.example.searchplant.model.Articles
import com.example.searchplant.model.Species
import com.example.searchplant.model.serviceSpecies
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ArticlesFragment : Fragment() {
    private lateinit var binding: FragmentSpeciesBinding
    private var db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSpeciesBinding.inflate(inflater, container,false)
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_articlesFragment_to_homeFragment)
        }

        val btnNavView = binding.bottomNavigationView1
        btnNavView.background = null
        val btnBack = binding.btnBack
        btnBack.background = null
        getDataArticles()

        binding.bottomNavigationView1.setOnNavigationItemReselectedListener{
            when(it.itemId) {
                R.id.home -> {
                    findNavController().navigate(R.id.action_articlesFragment_to_homeFragment)
                }
                R.id.profile -> {
                    // Respond to navigation item 2 reselection
                }
            }
        }
        return binding.root
    }
    private fun getDataArticles(){
        var list: ArrayList<Articles> = ArrayList()
        db = FirebaseFirestore.getInstance()
        db.collection("ARTICLES")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    for (document in it.result) {
                        val myData = document.toObject(Articles::class.java)
                        list.add(myData)
                        Log.d(ContentValues.TAG, "---------------------$myData")
                        val adapter = ArticlesAdapter(requireActivity(),list)
                        binding.listSpec.adapter = adapter
                        binding.listSpec.isClickable = true
                        Log.d(ContentValues.TAG, "---------------------AAAAAAAAAAAAAAAA$myData")
                        binding.listSpec.setOnItemClickListener { parent, vixew, position, id ->
                            sendData(list[position].getImageArticles().toString())
                            findNavController().navigate(R.id.action_articlesFragment_to_detailArticlesFragment)
                        }
                    }
                }

            }
    }
    private fun sendData(textSend:String)
    {
        val bundle = Bundle()
        bundle.putString("data",textSend)
        parentFragmentManager.setFragmentResult("articles",bundle)
    }

}