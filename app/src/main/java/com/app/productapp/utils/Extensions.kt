package com.app.productapp.utils

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.FileProvider
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.app.productapp.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.io.File

fun Context.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Context.createUri(): Uri {
    val imageFile = File(applicationContext.filesDir, "camera_image.jpg")
    return FileProvider.getUriForFile(applicationContext, "$packageName.fileProvider", imageFile)
}

fun AppCompatEditText.getValueOrEmpty(defaultValue: String = ""): String {
    return if (this.text.isNullOrBlank()) {
        defaultValue
    } else {
        this.text.toString()
    }
}

fun AppCompatEditText.isEmpty(): Boolean {
    return this.text.isNullOrBlank()
}

fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

fun ImageView.loadImage(url: String?, progressDrawable: CircularProgressDrawable) {
    val options = RequestOptions().placeholder(progressDrawable).error(R.mipmap.ic_launcher_round)
    Glide.with(this.context).setDefaultRequestOptions(options).load(url).into(this)
}