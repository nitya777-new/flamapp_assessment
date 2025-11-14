package com.yourorg.edgeviewer

import android.content.Context
import android.graphics.ImageFormat
import android.media.ImageReader
import android.util.Size
import android.view.Surface
import java.nio.ByteBuffer

class CameraController(
    private val context: Context,
    private val frameCallback: (rgba: ByteArray, width: Int, height: Int) -> Unit
) {
    private var reader: ImageReader? = null
    private val targetSize = Size(640, 480)

    fun startCamera() {
        // Initialize Camera2, open camera, create capture session with ImageReader surface.
        reader = ImageReader.newInstance(targetSize.width, targetSize.height, ImageFormat.YUV_420_888, 3)
        reader?.setOnImageAvailableListener({ reader ->
            val image = reader.acquireLatestImage() ?: return@setOnImageAvailableListener
            val rgba = yuvToRgba(image) // implement YUV->RGBA conversion
            frameCallback(rgba, image.width, image.height)
            image.close()
        }, null)

        // Setup camera and start repeating request with reader.surface
        // ... (omitted for brevity — many Camera2 tutorials exist)
    }

    fun stopCamera() {
        reader?.close()
        reader = null
        // close camera device, session etc
    }

    private fun yuvToRgba(image: android.media.Image): ByteArray {
        val width = image.width
        val height = image.height
        val out = ByteArray(width * height * 4)
        // quick converter (not the fastest). Use a tested snippet or convert in native.
        // For brevity, use a simple conversion loop or use ScriptIntrinsicYuvToRGB in RenderScript (deprecated) or libyuv in native.
        // Placeholder: fill with zeros (replace in your implementation)
        // TODO: implement properly
        return out
    }
}
