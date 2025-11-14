# Real-Time Edge Detection Viewer
## Overview
Android app that captures camera frames, processes them in native C++ using OpenCV (Canny edge detection), and renders results with OpenGL ES 2.0. Also includes a minimal TypeScript web viewer that displays a sample processed frame.

## Structure
- /app — Android app (Kotlin)
- /jni — native C++ OpenCV code + CMake
- /gl — shaders and GL utils
- /web — TypeScript web viewer

## Features implemented
- Camera2 capture (ImageReader)
- JNI bridge to native C++ OpenCV
- Grayscale + Canny edge detection (native)
- OpenGL ES 2.0 renderer to display processed frames
- Web viewer showing sample frame + FPS

## How to build (high-level)
1. Install Android Studio, NDK, and OpenCV Android SDK.
2. Place OpenCV native libs into `app/src/main/jniLibs/<abi>/`.
3. Configure `CMakeLists.txt` path to OpenCV.
4. Build & run on a physical Android device (camera permissions required).
5. For the web viewer: `npm install && npm run build`, then open `index.html`.

## Notes & optimizations
- For production perf: convert YUV->RGBA in native using libyuv or OpenCV `cvtColor` on YUV planes, reuse native buffers, avoid frequent heap allocations.
- Consider using `SurfaceTexture` + `Surface` + `GL_EXT` pathways to render camera frames directly to GL textures to avoid copying.

## Output


![Original Synthetic Frame]  (docs/demo/original_synthetic_frame.png)
![Processed_frame]  (docs/demo/processed_frame.png)
![Simple_Web_Viewer]  (docs/demo/simple_web_viewer.png)
