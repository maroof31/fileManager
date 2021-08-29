package com.maroof.filemanager

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class Fileadapter(private val listner: MainActivity): RecyclerView.Adapter<fileViewholder>() {
    private val items: ArrayList<Directories> = ArrayList<Directories>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): fileViewholder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_file,parent,false)
        val viewholder=fileViewholder(view)
        view.setOnClickListener{
            listner.itemClick(items[viewholder.adapterPosition])
        }
        return viewholder
    }

    override fun onBindViewHolder(holder: fileViewholder, position: Int) {
        val currentitem=items[position]
         val currentfileextension=currentitem.fileextenstion.toString()
       if(currentfileextension=="jpg" ||
           currentfileextension=="gif" ||
           currentfileextension=="jpeg" ||
               currentfileextension=="png"){
           val path: Uri = Uri.fromFile(currentitem.file)

           Glide.with(holder.icon.context).load(path).into(holder.icon)
       }else if(currentfileextension=="zip"){
           Glide.with(holder.icon.context).load(R.drawable.zipicon).into(holder.icon)
       }
      else  if (currentfileextension=="mp4"){
            Glide.with(holder.icon.context).load(R.drawable.videoicon).into(holder.icon)
        }else if(currentfileextension=="mp3"){
            Glide.with(holder.icon.context).load(R.drawable.musicicon).into(holder.icon)
        }else if (currentfileextension=="pdf"){
            Glide.with(holder.icon.context).load(R.drawable.pdficon).into(holder.icon)
        }else{
            Glide.with(holder.icon.context).load(R.drawable.icon).into(holder.icon)
        }
       holder.dirname.text=currentitem.filerelativePath


    }
    fun updatefiles(updatedfiles: ArrayList<Directories>) {
        items.clear()
        items.addAll(updatedfiles)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int
    {
        return items.size
    }
}
class fileViewholder(itemView: View) : RecyclerView.ViewHolder(itemView)
{
   val dirname=itemView.findViewById<TextView>(R.id.textView)
    val icon=itemView.findViewById<ImageView>(R.id.imageView)

}
interface  OnItemClick
{
   fun itemClick(item: Directories)
}