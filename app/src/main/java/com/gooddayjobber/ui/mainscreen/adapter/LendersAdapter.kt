package com.gooddayjobber.ui.mainscreen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gooddayjobber.R
import com.gooddayjobber.model.SozoResponse
import com.squareup.picasso.Picasso

class LendersAdapter(private var data: List<SozoResponse>, private val onClickListener: OnClickListener) :
    RecyclerView.Adapter<LendersAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val lenderName: TextView = itemView.findViewById(R.id.tvName)
        val lenderSlogan: TextView = itemView.findViewById(R.id.tvSlogan)
        val image: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lenders_grid_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.itemView.setOnClickListener {
            onClickListener.onClick(item)
        }
        holder.lenderName.text = item.name ?: ""
        holder.lenderSlogan.text = item.slogan ?: ""

        Picasso.get().load(item.image).into(holder.image)

    }

    override fun getItemCount(): Int = data.size

    class OnClickListener(val clickListener: (item: SozoResponse) -> Unit){
        fun onClick(item: SozoResponse) = clickListener(item)
    }

    fun updateData(newData: List<SozoResponse>) {
        data = newData
        notifyDataSetChanged()
    }
}