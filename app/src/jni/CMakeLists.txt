cmake_minimum_required(VERSION 3.10)
project(edgeprocessor)

set(OpenCV_DIR ${CMAKE_SOURCE_DIR}/../opencv/sdk/native/jni) # adjust path
find_package(OpenCV REQUIRED COMPONENTS core imgproc imgcodecs)

add_library(native-lib SHARED native-lib.cpp edge_processor.cpp)
include_directories(${OpenCV_INCLUDE_DIRS})

find_library(log-lib log)
target_link_libraries(native-lib ${OpenCV_LIBS} ${log-lib})
