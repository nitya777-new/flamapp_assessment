#ifndef EDGE_PROCESSOR_H
#define EDGE_PROCESSOR_H
#include <vector>

void process_rgba_frame(const std::vector<unsigned char>& inbuf,
                        std::vector<unsigned char>& outbuf,
                        int width, int height,
                        bool applyEdges);

#endif
