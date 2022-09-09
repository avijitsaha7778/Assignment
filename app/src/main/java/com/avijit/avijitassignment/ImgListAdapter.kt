package com.avijit.avijitassignment

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ImgListAdapter(var context: Context): RecyclerView.Adapter<ImgListAdapter.ImgListViewHolder>() ,
    Filterable {

    private var nameList : MutableList<Clips> = ArrayList()


    interface DialogInterface{
        fun onTicketClick(mClips: Clips)
    }

    class ImgListViewHolder(item: View): RecyclerView.ViewHolder(item){

        val img = item.findViewById<ImageView>(R.id.coinImg)
        val name = item.findViewById<TextView>(R.id.name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImgListViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.imglist_item, parent, false)
        return ImgListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImgListViewHolder, position: Int) {
       var mClips = nameList[position]

        holder.name.text = "Ticket ID ${mClips.label}"
        Picasso.with(context)
            .load(mClips.labelImg)
            .into(holder.img);
    }

    override fun getItemCount(): Int {
       return  nameList.size
    }

    fun addItem(nameListVal : MutableList<Clips>){
        nameList.clear()
        nameList.addAll(nameListVal)
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter? {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    nameList = nameList
                } else {
                    val filteredList: MutableList<Clips> = ArrayList<Clips>()
                    for (row in nameList) {
                        if (row.label!!.lowercase().contains(charString.lowercase()) ||
                            row.label!!.contains(charSequence)
                        ) {
                            filteredList.add(row)
                        }
                    }
                    nameList = filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = nameList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                nameList = filterResults.values as ArrayList<Clips>
                notifyDataSetChanged()
            }
        }
    }
}