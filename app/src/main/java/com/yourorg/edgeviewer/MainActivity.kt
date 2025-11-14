package com.yourorg.edgeviewer

import android.app.Activity
import android.os.Bundle
import android.view.TextureView
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : Activity() {
    private lateinit var cameraController: CameraController
    private lateinit var glView: GLTextureView
    private lateinit var fpsText: TextView
    private var showEdges = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // create a simple layout with a TextureView or custom GLTextureView

        glView = findViewById(R.id.glTextureView)
        fpsText = findViewById(R.id.fpsText)
        val toggleBtn: Button = findViewById(R.id.toggleBtn)

        cameraController = CameraController(this) { rgbaBuffer, width, height ->
            // Called on camera thread with a ByteArray or ByteBuffer containing RGBA pixels
            glView.updateFrame(rgbaBuffer, width, height, showEdges)
        }

        toggleBtn.setOnClickListener {
            showEdges = !showEdges
        }
    }

    override fun onResume() {
        super.onResume()
        cameraController.startCamera()
        glView.onResume()
    }

    override fun onPause() {
        glView.onPause()
        cameraController.stopCamera()
        super.onPause()
    }
}
