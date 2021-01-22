package com.keremturker.contactreader.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.keremturker.contactreader.R
import com.keremturker.contactreader.model.Contact

class ContactAdapter(var contact_List: ArrayList<Contact>) :
    RecyclerView.Adapter<ContactAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.contact_line, parent, false)

        return ViewHolder(inflater)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.txt_name.text = contact_List[position].name
        holder.txt_number.text = contact_List[position].number

    }

    override fun getItemCount(): Int {

        return contact_List.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val txt_name: TextView = itemView.findViewById(R.id.txt_isim)
        val txt_number: TextView = itemView.findViewById(R.id.txt_number)

    }

}