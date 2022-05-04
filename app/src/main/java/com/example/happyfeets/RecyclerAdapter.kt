package com.example.happyfeets

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private val data_text: Array<String>,
                      private val data_desc: Array<String>):
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {
        var textView: TextView

        init {
            textView = view.findViewById(R.id.CarrdViewTextView)
            view.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val context = itemView.context
            val position: Int = bindingAdapterPosition
            val intent = Intent(context, DetailsActivity::class.java).apply {
                putExtra("TITLE", data_text[position])
                putExtra("DESC", data_desc[position])
            }
            context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_view, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView.text = data_text[position]
    }

    override fun getItemCount() = data_text.size
}