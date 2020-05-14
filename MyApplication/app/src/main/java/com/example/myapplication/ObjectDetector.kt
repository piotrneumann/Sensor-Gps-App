package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_object_detector.*
import kotlinx.android.synthetic.main.activity_text_recognition.*

class ObjectDetector : AppCompatActivity() {

    val pickPhotoRequestCode = 101;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_object_detector)
        button_det.setOnClickListener { view ->
            pickImage()
        }
    }

    private fun pickImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, pickPhotoRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                pickPhotoRequestCode -> {
                    val bitmap = getImageFromData(data)
                    bitmap?.apply {
                        contentIV2.setImageBitmap(this)
                        processImageTextRec(bitmap)
                    }
                }
            }
        }
        super.onActivityResult(
            requestCode, resultCode,
            data
        )
    }

    private fun getImageFromData(data: Intent?): Bitmap? {
        val selectedImage = data?.data
        return MediaStore.Images.Media.getBitmap(
            this.contentResolver,
            selectedImage
        )
    }

    private fun processImageTextRec(bitmap: Bitmap) {
        val visionImg = FirebaseVisionImage.fromBitmap(bitmap)
        val objectDetector = FirebaseVision.getInstance().getOnDeviceObjectDetector()
        objectDetector.processImage(visionImg).addOnSuccessListener { tags ->
            tagsTV2.text = tags.toString()
        }
    }
}
