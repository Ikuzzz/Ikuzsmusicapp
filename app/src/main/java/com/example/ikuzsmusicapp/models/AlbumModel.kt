package com.example.ikuzsmusicapp.models

import android.graphics.Bitmap

data class AlbumModel(
    var album : String,
//    var _data : String,
    var _id : Long,
    var albumId: Long?,
//    var album_art : Bitmap?,
    var numsongs : Int,
    var artist : String,
//    var album_art : String
)