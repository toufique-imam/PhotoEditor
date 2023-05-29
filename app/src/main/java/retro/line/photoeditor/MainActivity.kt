package retro.line.photoeditor

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import retro.line.photoeditor.databinding.ActivityMainBinding
import retro.line.photoeditor.views.EditPhotoAdapter
import retro.line.photoeditor.views.EditPhotoCellDelegate

class MainActivity : AppCompatActivity(), EditPhotoCellDelegate {
    private lateinit var binding: ActivityMainBinding
    private lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>
    private lateinit var adapter: EditPhotoAdapter
    private var imageData: MutableList<Uri> = mutableListOf()
    private var selectedPositionInData: Int = RecyclerView.NO_POSITION

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Setup the recycler view
        val recyclerView = binding.recyclerViewPhotos
        adapter = EditPhotoAdapter(imageData)
        recyclerView.adapter = adapter
        adapter.clickListener = this
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager

        // Setup the photo picker
        pickMedia = registerForActivityResult(PickVisualMedia()) { uri ->
            if (uri != null) {
                Log.e("PhotoPicker", "Selected URI: $uri")
                adapter.addImage(uri)
            } else {
                Log.e("PhotoPicker", "No media selected")
            }
        }
        // Setup the button to launch the photo picker
        binding.buttonPickImage.setOnClickListener {
            // Launch the photo picker and let the user choose only images.
            pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
        }
    }

    private fun setImageFromURI(uri: Uri) {
        binding.canvasViewMain.setImageUri(uri)
    }

    override fun onEditPhoto(position: Int) {
        val uri = imageData[position]
        selectedPositionInData = position
        setImageFromURI(uri)
    }

    override fun onDeletePhoto(position: Int) {
        if (selectedPositionInData == position) {
//            binding.imageViewMain.setImageResource(0)
        }
    }
}