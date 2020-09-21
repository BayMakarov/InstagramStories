package com.ozan.myapplication.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.ozan.myapplication.R

class StoryAdapter : RecyclerView.Adapter<StoryAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var storyPhoto : ImageView
        lateinit var storyVideo : VideoView




    }






    override fun getItemCount(): Int {

        return 0
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }
}