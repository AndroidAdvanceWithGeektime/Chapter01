cmake_minimum_required(VERSION 3.4.1)
project(dcrash)

set(ENABLE_INPROCESS ON)
#set(ENABLE_OUTOFPROCESS ON)
set(ENABLE_LIBCORKSCREW ON)
set(ENABLE_LIBUNWIND ON)
set(ENABLE_LIBUNWINDSTACK ON)
set(ENABLE_CXXABI ON)
set(ENABLE_STACKSCAN ON)

# Setting up definitions. The same as used for NDCrash.
if (${ENABLE_INPROCESS})
    add_definitions(-DENABLE_INPROCESS)
endif()
if (${ENABLE_OUTOFPROCESS})
    add_definitions(-DENABLE_OUTOFPROCESS)
endif()

set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -Werror=implicit-function-declaration")
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11 ")

# breakpad
include_directories(external/libbreakpad/src external/libbreakpad/src/common/android/include)
add_subdirectory(external/libbreakpad)
list(APPEND LINK_LIBRARIES breakpad)

# ndcrash
include_directories(external/ndcrash/include)
add_subdirectory(external/ndcrash)
list(APPEND LINK_LIBRARIES ndcrash)

add_library(dcrash SHARED
        src/ndcrash.c
        src/breakpad.cpp)
target_link_libraries(dcrash ${LINK_LIBRARIES}
        ndcrash
        log)