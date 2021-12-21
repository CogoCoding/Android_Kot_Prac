package com.example.kot6.kot9

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kot6.R

class QuotePagerAdapter(
    private val quotes: List<Quote>,
    private var isNameRevealed:Boolean
): RecyclerView.Adapter<QuotePagerAdapter.QuoteViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        QuoteViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_quote,parent,false)
        )


    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val actualPos = position%quotes.size
        holder.bind(quotes[actualPos],isNameRevealed)
    }

    override fun getItemCount(): Int {
       return quotes.size
    }

    class QuoteViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        private val quoteTv:TextView = itemView.findViewById(R.id.quoteTv)
        private val nameTv:TextView = itemView.findViewById(R.id.nameTv)

        fun bind(quote:Quote,isNameRevealed:Boolean){
            quoteTv.text ="\"${quote.quote}\""
            if(isNameRevealed){
                nameTv.text = "-${quote.name}"
                nameTv.visibility = View.VISIBLE
            }else{nameTv.visibility =View.GONE}
        }
    }
}