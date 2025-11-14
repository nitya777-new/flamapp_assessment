package com.yourorg.edgeviewer

object NativeBridge {
    init { System.loadLibrary("native-lib") }

    external fun processFrame(inputRgba: ByteArray, width: Int, height: Int, applyEdges: Boolean): ByteArray
}
