package com.app.productapp.ui.add

import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.app.productapp.R
import com.app.productapp.databinding.ActivityAddProductBinding
import com.app.productapp.utils.*

class AddProductActivity : AppCompatActivity(), MyBottomSheetDialogFragment.OptionClickListener {

    private lateinit var binding: ActivityAddProductBinding
    private lateinit var imageUri: Uri
    private var selectedImageCount: Int = 0

    private lateinit var selectedImageUri: ArrayList<Uri>
    private lateinit var imagesAdapter: ImagesAdapter

    private val requestMultiplePermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->

            Log.e("Permissions",
                "Camera: ${result[Manifest.permission.CAMERA]}, Gallery: ${result[Manifest.permission.READ_EXTERNAL_STORAGE]}")

            val hasPermissions =
                result[Manifest.permission.CAMERA] == true && result[Manifest.permission.READ_EXTERNAL_STORAGE] == true
            if (hasPermissions) {
                if (selectedImageCount < 5) {
                    MyBottomSheetDialogFragment(this).apply {
                        show(supportFragmentManager, tag)
                    }
                } else {
                    showToast(getString(R.string.msg_image_selection_restriction))
                }
            } else {
                showToast(getString(R.string.msg_grant_permission))
            }
        }

    private val requestCameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { result ->
            try {
                if (result) {
                    notifyImageList(imageUri)
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }

    private val requestImagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { result ->
            if (result != null) notifyImageList(result)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        selectedImageUri = arrayListOf()
        imageUri = createUri()
        setupListeners()
        setupImagesAdapter()
    }

    private fun setupListeners() {
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.ivProduct.setOnClickListener {
            requestMultiplePermissionLauncher.launch(
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA))
        }
        binding.btnAdd.setOnClickListener {
            prepareDataToUpload()
        }
    }

    private fun prepareDataToUpload() {
        if (validateData()) {
            val title: String = binding.etTitle.getValueOrEmpty("")
            val description: String = binding.etDescription.getValueOrEmpty("")
            val price: String = binding.etPrice.getValueOrEmpty("0")
            val discount: String = binding.etDiscount.getValueOrEmpty("0.0")
            showToast("$title, $description, $price, $discount, ${selectedImageUri.size}")
        }
    }

    private fun validateData(): Boolean {
        return if (binding.etTitle.isEmpty()) {
            showToast(getString(R.string.msg_validate_name))
            false
        } else if (binding.etDescription.isEmpty()) {
            showToast(getString(R.string.msg_validate_description))
            false
        } else if (binding.etPrice.isEmpty()) {
            showToast(getString(R.string.msg_validate_price))
            false
        } else if (binding.etDiscount.isEmpty()) {
            showToast(getString(R.string.msg_validate_discount))
            false
        } else {
            true
        }
    }

    private fun setupImagesAdapter() {
        imagesAdapter = ImagesAdapter()
        binding.rvImageList.apply {
            adapter = imagesAdapter
        }
    }

    private fun notifyImageList(resultUri: Uri) {
        selectedImageCount++
        selectedImageUri.add(resultUri)
        imagesAdapter.refreshData(selectedImageUri)
    }

    override fun onOptionClick(selectedOption: Constants.MEDIA_OPTIONS) {
        when (selectedOption) {
            Constants.MEDIA_OPTIONS.CAMERA -> {
                dispatchTakePictureIntent()
            }
            Constants.MEDIA_OPTIONS.GALLERY -> {
                pickImageFromGallery()
            }
        }
    }

    private fun dispatchTakePictureIntent() {
        requestCameraLauncher.launch(imageUri)
    }

    private fun pickImageFromGallery() {
        val visualMediaRequest = PickVisualMediaRequest.Builder()
            .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly).build()
        requestImagePickerLauncher.launch(visualMediaRequest)
    }

}