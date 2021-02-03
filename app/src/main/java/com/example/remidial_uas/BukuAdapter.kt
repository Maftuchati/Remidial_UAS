package com.example.remidial_uas

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.remidial_uas.database.Buku
import kotlinx.android.synthetic.main.adapter_buku.view.*
import java.nio.file.Files.size

class BukuAdapter (private val buku: ArrayList<Buku>, private val listener: onAdapterListener) :
    RecyclerView.Adapter<BukuAdapter.BukuViewHolder>() {

        class BukuViewHolder(val view: View) : RecyclerView.ViewHolder(view)

        fun setData(list: List<Buku>){
            buku.clear()
            buku.addAll(list)
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BukuViewHolder {
            return BukuViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.adapter_buku, parent,false)
            )
        }

        override fun onBindViewHolder(holder: BukuViewHolder, position: Int) {
            val b = buku[position]
            holder.view.text_nama.text = b.judul_buku
            holder.view.text_nama.setOnClickListener {
                listener.onClick(b)
            }
            holder.view.icon_delete.setOnClickListener {
                listener.onDelete(b)
            }
            holder.view.icon_edit.setOnClickListener {
                listener.onUpdate(b)
            }
        }

        override fun getItemCount(): Int {
            Log.d("count", buku.size.toString())
            return buku.size
        }

        interface onAdapterListener{
            fun onClick(buku: Buku)
            fun onDelete(buku: Buku)
            fun onUpdate(buku: Buku)
        }
}