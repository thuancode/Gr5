package com.example.searchplant.view.screen

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.searchplant.R
import com.example.searchplant.databinding.FragmentDetailArticlesBinding
import com.example.searchplant.databinding.FragmentSpeciesBinding
import com.example.searchplant.model.Articles
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.File


class DetailArticlesFragment : Fragment() {

    lateinit var binding: FragmentDetailArticlesBinding
    private var db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailArticlesBinding.inflate(inflater, container,false)
        binding.bottomNavigationView2.background = null

        parentFragmentManager.setFragmentResultListener("articles",this,{requestKey,result->
            val textData = result.getString("data").toString()
            getDataArticles(textData)
        })

        binding.bottomNavigationView2.setOnNavigationItemReselectedListener{
            when(it.itemId) {
                R.id.home -> {
                    findNavController().navigate(R.id.action_detailArticlesFragment_to_homeFragment)
                }
                R.id.profile -> {
                    // Respond to navigation item 2 reselection
                }
            }
        }
        return binding.root
    }
    private fun getDataArticles(textData: String) {
        db = FirebaseFirestore.getInstance()
        db.collection("ARTICLES")
            .get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    for(document in it.result)
                    {
                        val myData = document.toObject(Articles::class.java)
                        if(myData.getImageArticles().toString() == textData)
                        {
                            binding.textTitleArt.text = myData.getTitleArticles()
                            binding.textType.text = myData.getType()
                            binding.textProperties.text = myData.getProperties()
                            binding.textDescription.text = myData.getDescription()
                            val storageRef = FirebaseStorage.getInstance().reference.child("articles/${myData.getImageArticles()}.jpg")
                            val localFile = File.createTempFile("tempArticles","jpg")
                            storageRef.getFile(localFile).addOnCompleteListener{
                                val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                                binding.imageArticles.setImageBitmap(bitmap)
                            }

                        }
                    }
                }
            }
    }
}