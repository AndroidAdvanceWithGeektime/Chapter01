cmake_minimum_required(VERSION 3.4.1)

set(BREAKPAD_ROOT ${CMAKE_CURRENT_SOURCE_DIR})

include_directories(${BREAKPAD_ROOT}/src ${BREAKPAD_ROOT}/src/common/android/include)


file(GLOB BREAKPAD_SOURCES_COMMON
        ${BREAKPAD_ROOT}/src/client/linux/crash_generation/crash_generation_client.cc
        ${BREAKPAD_ROOT}/src/client/linux/dump_writer_common/thread_info.cc
        ${BREAKPAD_ROOT}/src/client/linux/dump_writer_common/ucontext_reader.cc
        ${BREAKPAD_ROOT}/src/client/linux/handler/exception_handler.cc
        ${BREAKPAD_ROOT}/src/client/linux/handler/minidump_descriptor.cc
        ${BREAKPAD_ROOT}/src/client/linux/log/log.cc
        ${BREAKPAD_ROOT}/src/client/linux/microdump_writer/microdump_writer.cc
        ${BREAKPAD_ROOT}/src/client/linux/minidump_writer/linux_dumper.cc
        ${BREAKPAD_ROOT}/src/client/linux/minidump_writer/linux_ptrace_dumper.cc
        ${BREAKPAD_ROOT}/src/client/linux/minidump_writer/minidump_writer.cc
        ${BREAKPAD_ROOT}/src/client/minidump_file_writer.cc
        ${BREAKPAD_ROOT}/src/common/convert_UTF.c
        ${BREAKPAD_ROOT}/src/common/md5.cc
        ${BREAKPAD_ROOT}/src/common/string_conversion.cc
        ${BREAKPAD_ROOT}/src/common/linux/elfutils.cc
        ${BREAKPAD_ROOT}/src/common/linux/file_id.cc
        ${BREAKPAD_ROOT}/src/common/linux/guid_creator.cc
        ${BREAKPAD_ROOT}/src/common/linux/linux_libc_support.cc
        ${BREAKPAD_ROOT}/src/common/linux/memory_mapped_file.cc
        ${BREAKPAD_ROOT}/src/common/linux/safe_readlink.cc

        )

file(GLOB BREAKPAD_ASM_SOURCE ${BREAKPAD_ROOT}/src/common/android/breakpad_getcontext.S
        )

set_source_files_properties(${BREAKPAD_ASM_SOURCE} PROPERTIES LANGUAGE C)

add_library(breakpad STATIC ${BREAKPAD_SOURCES_COMMON} ${BREAKPAD_ASM_SOURCE})

target_link_libraries(breakpad log)
