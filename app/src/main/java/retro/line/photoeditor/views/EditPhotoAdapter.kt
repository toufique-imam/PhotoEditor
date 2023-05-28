package retro.line.photoeditor.views

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import retro.line.photoeditor.databinding.PhotoCellBinding


class EditPhotoAdapter(private var imageData: MutableList<Uri>) :
    RecyclerView.Adapter<EditPhotoCellView>(), EditPhotoCellDelegate {

    var clickListener: EditPhotoCellDelegate? = null

    fun addImage(uri: Uri) {
        imageData.add(uri)
        notifyItemInserted(imageData.size - 1)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EditPhotoCellView {
        val binding = PhotoCellBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EditPhotoCellView(binding)
    }

    override fun onBindViewHolder(holder: EditPhotoCellView, position: Int) {
        holder.setImageFromURI(imageData[position])
        holder.delegate = this
    }

    override fun getItemCount(): Int = imageData.size


    companion object {
    }

    override fun onEditPhoto(position: Int) {
        if (position == RecyclerView.NO_POSITION) return
        clickListener?.onEditPhoto(position)
    }

    override fun onDeletePhoto(position: Int) {
        if (position == RecyclerView.NO_POSITION) return
        imageData.removeAt(position)
        notifyItemRemoved(position)
        clickListener?.onDeletePhoto(position)
    }
}
