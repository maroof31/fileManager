package com.maroof.filemanager

import android.media.MediaController2
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.io.File


class MainActivity : AppCompatActivity(), OnItemClick{
  lateinit var adapter:Fileadapter
  lateinit var imagepreview:ImageView
  lateinit var videoview:VideoView
    val filelist=ArrayList<Directories>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val builder = VmPolicy.Builder()
//        StrictMode.setVmPolicy(builder.build())
         imagepreview=findViewById<ImageView>(R.id.imagepreview)
        videoview=findViewById(R.id.videoView)
        videoview.visibility=View.GONE
        imagepreview.visibility=View.GONE
             val root=File(Environment.getExternalStorageDirectory().absolutePath)
        ListDir(root)
        val rv=findViewById<RecyclerView>(R.id.recyclervieww)
        rv.layoutManager=LinearLayoutManager(this)
         adapter=Fileadapter(this)
        rv.adapter=adapter
        adapter.updatefiles(filelist)

    }


    fun ListDir(f: File) {
        val files = f.listFiles()
        filelist.clear()
        for (file in files) {
            val currFile=file

                 val fileExtension=file.extension
            Log.d("extension","${fileExtension.toString()}")
                val abpath=file.path
                val rvpath=file.relativeTo(f).toString()
            val file=Directories(currFile,fileExtension,abpath,rvpath)
            filelist.add(file)
        }
            Log.d("listoffiles",filelist.toString())
    }

    override fun itemClick(directories: Directories) {
        if(directories.fileextenstion==""){
             openlist(directories.file)
        }else{
            openSelecteditem(directories.file)
        }
    }


   fun openlist(fl:File){
           filelist.clear()
       val newlist=fl.listFiles()
       for(file in newlist){
           val currFile=file
           val fileExtension=file.extension
           Log.d("extension","${fileExtension.toString()}")
           val abpath=file.path
           val rvpath=file.relativeTo(fl).toString()
           val file=Directories(currFile,fileExtension,abpath,rvpath)
           filelist.add(file)
           adapter.updatefiles(filelist)
       }
   }

    fun openSelecteditem(fl:File){
        val path: Uri = Uri.fromFile(fl)
//        val fileOpenintent= Intent(Intent.ACTION_VIEW)
//        fileOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//        fileOpenintent.setDataAndType(path, "image/*")
//        try {
//            startActivity(fileOpenintent)
//        } catch (e: ActivityNotFoundException) {
//            Toast.makeText(this, "no program to open this file", Toast.LENGTH_SHORT).show()
//        }
        if(fl.extension=="jpg" ||
            fl.extension=="gif" ||
            fl.extension=="jpeg" ||
            fl.extension=="png") {
            imagepreview.visibility = View.VISIBLE
            Glide.with(this).load(path).into(imagepreview)

        }else if (fl.extension=="mp4"){
            videoview.visibility=View.VISIBLE
            videoview.setVideoPath(fl.absolutePath)
            val mediacontroller= MediaController(this)
            mediacontroller.setAnchorView(videoview)
            videoview.setMediaController(mediacontroller)
        }

    }

    override fun onBackPressed() {
        imagepreview.visibility=View.GONE
        videoview.visibility=View.GONE
    }
}