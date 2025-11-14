#include "edge_processor.h"
#include <opencv2/opencv.hpp>

using namespace cv;

void process_rgba_frame(const std::vector<unsigned char>& inbuf,
                        std::vector<unsigned char>& outbuf,
                        int width, int height,
                        bool applyEdges) {
    // inbuf: RGBA bytes (width*height*4)
    Mat rgba(height, width, CV_8UC4, (void*)inbuf.data());
    Mat bgr;
    cvtColor(rgba, bgr, COLOR_RGBA2BGR);

    if (applyEdges) {
        Mat gray, edges, edgesBgr;
        cvtColor(bgr, gray, COLOR_BGR2GRAY);
        // Canny parameters can be tuned
        Canny(gray, edges, 50, 150);
        // convert single channel edges to 3-channel BGR
        cvtColor(edges, edgesBgr, COLOR_GRAY2BGR);
        // convert back to RGBA for output
        Mat outRgba;
        cvtColor(edgesBgr, outRgba, COLOR_BGR2RGBA);
        outbuf.assign(outRgba.data, outRgba.data + outRgba.total() * outRgba.elemSize());
    } else {
        // just pass through (optionally convert to RGBA)
        Mat out;
        cvtColor(bgr, out, COLOR_BGR2RGBA);
        outbuf.assign(out.data, out.data + out.total() * out.elemSize());
    }
}
