cmake_minimum_required(VERSION 3.22.1)
project("minelauncher2")

add_library(minelauncher2 SHARED native-lib.cpp)

find_library(log-lib log)
target_link_libraries(minelauncher2 ${log-lib})
