cmake_minimum_required(VERSION 3.10.2)
# cmake_minimum_required(VERSION 3.6.0)

project("opengl")

#设置编译指令
set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -Wall")
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11 -fno-rtti -fno-exceptions -Wall")
#如果当前系统版本过低设置拒绝编译
if (${ANDROID_PLATFORM_LEVEL} LESS 11)
  message(FATAL_ERROR "OpenGL 2 is not supported before API level 11 (currently using ${ANDROID_PLATFORM_LEVEL}).")
  return()
elseif (${ANDROID_PLATFORM_LEVEL} LESS 18)
  add_definitions("-DDYNAMIC_ES3")
  set(OPENGL_LIB GLESv2)
else ()
  set(OPENGL_LIB GLESv3)
endif (${ANDROID_PLATFORM_LEVEL} LESS 11)

add_library( # Sets the name of the library.
  opengl
  SHARED
  native-lib_Triangle.cpp
  native-lib_Rect.cpp
  native-lib_Cube.cpp)

find_library( # Sets the name of the path variable.
        log-lib
        log)

target_link_libraries( # Specifies the target library.
  opengl
  ${OPENGL_LIB}
  android
  EGL
  m
  ${log-lib})

# target_link_libraries(triangle-lib
#   ${OPENGL_LIB}
#   android
#   EGL
#   log
#   m)

