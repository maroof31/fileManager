package com.maroof.filemanager

import java.io.File

data class Directories(
    val file:File,
    val fileextenstion:String,
    val fileabsolutepath:String,
    val filerelativePath:String
)