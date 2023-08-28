package com.jamessc94.recipe.ui.dialog

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.jamessc94.recipe.RecipeApp.Companion.imgPath
import com.jamessc94.recipe.databinding.DialogCameraStorageBinding
import com.jamessc94.recipe.ui.details.DetailsVM
import com.jamessc94.recipe.utils.setWidthPercent
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

@AndroidEntryPoint
class DialogCameraStorage : DialogFragment() {

    companion object {
        fun newInstance(): DialogCameraStorage {
            return DialogCameraStorage()
        }
    }

    private val vm : DetailsVM by viewModels()
    lateinit var binding : DialogCameraStorageBinding
    var image_uri: Uri? = null

    val cameraActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == Activity.RESULT_OK) {
            image_uri?.let {
                createFileFromContentUri(it).apply {
                    imgPath = this.path
                    dismiss()
                }
            }
        }
    }

    val galleryActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == Activity.RESULT_OK) {
            image_uri = result.data?.data

            image_uri?.let {
                createFileFromContentUri(it).apply {
                    imgPath = this.path
                    dismiss()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogCameraStorageBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        binding.dialogCameraStorageCamera.setOnClickListener {
            ContentValues().apply {
                put(MediaStore.Images.Media.TITLE, "New Picture")
                put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")

                image_uri = requireActivity().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, this)

                Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                    putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
                    cameraActivityResultLauncher.launch(this)
                }
            }
        }

        binding.dialogCameraStorageGallery.setOnClickListener {
            val galleryIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            galleryActivityResultLauncher.launch(galleryIntent)
        }

        binding.dialogCameraStorageClose.setOnClickListener {
            dismiss()

        }

        return binding.root

    }

    override fun onResume() {
        super.onResume()

        setWidthPercent(90)

    }

    private fun createFileFromContentUri(fileUri : Uri) : File {

        var fileName : String = ""

        fileUri.let { returnUri ->
            requireActivity().contentResolver.query(returnUri,null,null,null)
        }?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            cursor.moveToFirst()
            fileName = cursor.getString(nameIndex)
        }

        val fileType: String? = fileUri.let { returnUri ->
            requireActivity().contentResolver.getType(returnUri)
        }

        val iStream : InputStream =
            requireActivity().contentResolver.openInputStream(fileUri)!!
        val outputDir : File = context?.cacheDir!!
        val outputFile : File = File(outputDir,fileName)
        copyStreamToFile(iStream, outputFile)
        iStream.close()
        return  outputFile
    }

    fun copyStreamToFile(inputStream: InputStream, outputFile: File) {
        inputStream.use { input ->
            val outputStream = FileOutputStream(outputFile)
            outputStream.use { output ->
                val buffer = ByteArray(4 * 1024) // buffer size
                while (true) {
                    val byteCount = input.read(buffer)
                    if (byteCount < 0) break
                    output.write(buffer, 0, byteCount)
                }
                output.flush()
            }
        }
    }
}