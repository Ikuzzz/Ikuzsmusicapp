package com.example.ikuzsmusicapp.adapters

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ikuzsmusicapp.R
import com.example.ikuzsmusicapp.models.AlbumModel
import com.example.ikuzsmusicapp.utils.Constant
import java.io.File


class LocalAlbumAdapter(
    var AlbumList : ArrayList<AlbumModel>,
    var context : Context
) : RecyclerView.Adapter<LocalAlbumAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun getContext():Context = itemView.context

        val albumArt : ImageView = itemView.findViewById(R.id.albumArt)
        val albumTitel : TextView = itemView.findViewById(R.id.albumTitel)
        val songsText : TextView = itemView.findViewById(R.id.songsText)
        val artistText : TextView = itemView.findViewById(R.id.artistText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(context).inflate(R.layout.album_rec_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val AlbumData : AlbumModel = AlbumList[position]
//        var url = LocalAlbumFragment.getArtUriFromMusicFile
        holder.albumTitel.text = AlbumData.album
        holder.songsText.text = AlbumData.numsongs.toString() + " Songs"
        holder.artistText.text = AlbumData.artist
        holder.albumArt.run {
            if (this != null) {
                File(
                    Constant.imaSongDir.absolutePath + "/album_art",
                    File(AlbumData.filePath).nameWithoutExtension
                ).also {
                    when {
                        it.exists() -> Glide.with(holder.getContext())
                            .load(it)
                            .placeholder(R.drawable.album_p)
                            .into(this)

                        else -> {
                            try {
                                val sArtworkUri =
                                    Uri.parse("content://media/external/audio/albumart")
                                val albumArtUri =
                                    ContentUris.withAppendedId(sArtworkUri, AlbumData.album)
                                Glide
                                    .with(holder.getContext())
                                    .load(albumArtUri)
                                    .placeholder(Shared.defBitmap.toDrawable(resources))
                                    .into(this)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return AlbumList.size
    }
}