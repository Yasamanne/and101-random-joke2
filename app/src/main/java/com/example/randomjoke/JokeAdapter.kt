package com.example.randomjoke

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class JokeAdapter(val jokeList: MutableList<String>) : RecyclerView.Adapter<JokeAdapter.JokeViewHolder>() {

    class JokeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val jokeText: TextView

        init {
            //this will contain code that will always run when this class is instantiated
            jokeText = view.findViewById(R.id.joke_text)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeAdapter.JokeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.joke_texts, parent, false)
        return JokeViewHolder(view)
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
//        Glide.with(holder.itemView)
//            .load(jokeList[position])
//            .centerCrop()
//            .into(holder.dogImage)

                holder.jokeText.text = jokeList[position]
        //can set an on click listener here if you want a toast to pop up on click

    }

    override fun getItemCount(): Int {
        //display the amount of photos in the list
        return jokeList.size
    }


}