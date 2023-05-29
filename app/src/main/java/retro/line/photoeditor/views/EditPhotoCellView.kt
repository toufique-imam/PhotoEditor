package retro.line.photoeditor.views

import android.net.Uri
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import retro.line.photoeditor.databinding.PhotoCellBinding

interface EditPhotoCellDelegate {
    fun onEditPhoto(position: Int)
    fun onDeletePhoto(position: Int)
}

class EditPhotoCellView(binding: PhotoCellBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private val imageView: ImageView = binding.imageViewPhotoCell
    private val deleteButton: Button = binding.buttonDeletePhotoCell
    private val editButton: Button = binding.buttonEditPhotoCell
    var delegate: EditPhotoCellDelegate? = null

    init {
        deleteButton.setOnClickListener {
            adapterPosition
            delegate?.onDeletePhoto(adapterPosition)
        }
        editButton.setOnClickListener {
            delegate?.onEditPhoto(adapterPosition)
        }
    }

    fun setImageFromURI(uri: Uri) {
        Glide.with(this.itemView).load(uri).centerCrop().into(this.imageView)
//        imageView.setImageURI(uri)
    }
}