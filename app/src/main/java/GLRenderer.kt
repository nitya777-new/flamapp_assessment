package com.yourorg.edgeviewer

import android.opengl.GLES20 
import android.opengl.GLSurfaceView
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class GLRenderer : GLSurfaceView.Renderer {
    @Volatile private var frameBuffer: ByteArray? = null
    private var width = 0
    private var height = 0
    private var textureId = -1

    fun updateFrame(rgba: ByteArray, w: Int, h: Int, applyEdges: Boolean) {
        // Optionally call JNI here to process: NativeBridge.processFrame(...)
        // For now assume rgba already processed result (or call processing before sending)
        frameBuffer = rgba
        width = w
        height = h
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        textureId = createTexture()
        GLES20.glClearColor(0f, 0f, 0f, 1f)
        // load shaders, create program, set up vertex arrays...
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        val fb = frameBuffer ?: return
        if (textureId != -1 && width > 0 && height > 0) {
            // upload texture
            val bb = ByteBuffer.allocateDirect(fb.size).order(ByteOrder.nativeOrder())
            bb.put(fb).position(0)
            bb.rewind()
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId)
            GLES20.glTexImage2D(
                GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, width, height, 0,
                GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, bb
            )
            // draw textured quad (use your shader program)
            drawFullScreenQuad()
        }
    }

    private fun createTexture(): Int {
        val textureIds = IntArray(1)
        GLES20.glGenTextures(1, textureIds, 0)
        val id = textureIds[0]
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, id)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE)
        return id
    }

    private fun drawFullScreenQuad() {
        // implement vertex attribs, drawArrays for triangle strip or two triangles
    }
}
