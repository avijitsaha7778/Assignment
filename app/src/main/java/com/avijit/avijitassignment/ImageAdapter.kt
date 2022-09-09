package com.avijit.avijitassignment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class ImageAdapter(var mContext: Context?) : RecyclerView.Adapter<ImageAdapter.ViewPagerHolder>(){
    private var imgList : MutableList<Data> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder {
        val binding = LayoutInflater.from(parent.context).inflate(R.layout.viewpager_item, parent, false)
        return ViewPagerHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewPagerHolder, position: Int) {

        Picasso.with(mContext)
            .load(imgList[position].src)
            .into(holder.img);
    }

    override fun getItemCount(): Int {
        return imgList.size
    }

    class ViewPagerHolder(private var itemHolderBinding: View) :
        RecyclerView.ViewHolder(itemHolderBinding) {
        val img = itemHolderBinding.findViewById<ImageView>(R.id.img)

    }

    fun addItem(imgData : MutableList<Data>){
        imgList.clear()
        imgList.addAll(imgData)
        notifyDataSetChanged()
    }

}