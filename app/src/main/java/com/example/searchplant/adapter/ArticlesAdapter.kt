package com.example.searchplant.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.searchplant.R
import com.example.searchplant.model.Articles
import com.example.searchplant.model.Species
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class ArticlesAdapter(var context: Context, private var listArticle:List<Articles>): BaseAdapter() {
    class ViewHolder(row: View) {
        var imageArticle: ImageView
        var titleArticle: TextView
        var likeArticle: ImageView
        var saveArticle: ImageView

        init {
            imageArticle = row.findViewById(R.id.ivArticle) as ImageView
            titleArticle= row.findViewById(R.id.tvArticleContent) as TextView
            likeArticle = row.findViewById(R.id.ivHeart) as ImageView
            saveArticle= row.findViewById(R.id.ivBookmark) as ImageView
        }
    }
    override fun getCount(): Int {
        return listArticle.size
    }

    override fun getItem(position: Int): Any {
        return listArticle[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view : View?
        val viewHolder : ArticlesAdapter.ViewHolder
        if(convertView == null)
        {
            val layoutInflater: LayoutInflater = LayoutInflater.from(context)
            view =layoutInflater.inflate(R.layout.article_item,null)
            viewHolder = ArticlesAdapter.ViewHolder(view)
            view.tag = viewHolder
        }else{
            view = convertView
            viewHolder = convertView.tag as ArticlesAdapter.ViewHolder
        }
        val art:Articles = getItem(position) as Articles
        viewHolder.titleArticle.text = art.getTitleArticles().toString()
        //// imageee
        val storageRef = FirebaseStorage.getInstance().reference.child("articles/${art.getImageArticles()}.jpg")
        val localFile = File.createTempFile("tempArticle","jpg")
        storageRef.getFile(localFile).addOnCompleteListener{
            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            viewHolder.imageArticle.setImageBitmap(bitmap)
        }
        ///
    return view!!
    }

}