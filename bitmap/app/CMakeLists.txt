cmake_minimum_required(VERSION 3.4.1)
add_library(
             native-lib
             SHARED
             src/main/cpp/native-lib.cpp )

add_library(libjpeg STATIC IMPORTED)
set_target_properties(libjpeg PROPERTIES IMPORTED_LOCATION ${CMAKE_SOURCE_DIR}/src/main/cpp/libs/libturbojpeg.a)
#引入头文件
include_directories(src/main/cpp/include)

target_link_libraries(
                       native-lib
                       libjpeg
                       jnigraphics
                       log)