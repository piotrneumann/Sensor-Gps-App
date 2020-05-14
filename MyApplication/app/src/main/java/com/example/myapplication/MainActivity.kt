package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val pickPhotoRequestCode = 101;

    private fun pickImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, pickPhotoRequestCode)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener { view ->
            pickImage()
        }

        button2.setOnClickListener {
            val intent = Intent(this, TextRecognition::class.java)
            startActivity(intent)
        }

        button3.setOnClickListener {
            val intent = Intent(this, ObjectDetector::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                pickPhotoRequestCode -> {
                    val bitmap = getImageFromData(data)
                    bitmap?.apply {
                        contentIV.setImageBitmap(this)
                        processImageTagging(bitmap)
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

    private fun processImageTagging(bitmap: Bitmap) {
        val visionImg = FirebaseVisionImage.fromBitmap(bitmap)
        val labeler =
            FirebaseVision.getInstance().getOnDeviceImageLabeler().processImage(visionImg)
                .addOnSuccessListener { tags ->
                    tagsTV.text = tags.joinToString(" ") {
                        it.text
                    }
                }
                .addOnFailureListener { ex ->
                    Log.wtf("LAB", ex)
                }
    }
}
