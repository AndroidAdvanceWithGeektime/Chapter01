set(LZMA_ROOT ${CMAKE_CURRENT_SOURCE_DIR}/../../lzma)

set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -D_7ZIP_ST -Wno-empty-body")
include_directories(${LZMA_ROOT}/C)
file(GLOB LZMA_SOURCES
    ${LZMA_ROOT}/C/7zAlloc.c
    ${LZMA_ROOT}/C/7zArcIn.c
    ${LZMA_ROOT}/C/7zBuf2.c
    ${LZMA_ROOT}/C/7zBuf.c
    ${LZMA_ROOT}/C/7zCrc.c
    ${LZMA_ROOT}/C/7zCrcOpt.c
    ${LZMA_ROOT}/C/7zDec.c
    ${LZMA_ROOT}/C/7zFile.c
    ${LZMA_ROOT}/C/7zStream.c
    ${LZMA_ROOT}/C/Aes.c
    ${LZMA_ROOT}/C/AesOpt.c
    ${LZMA_ROOT}/C/Alloc.c
    ${LZMA_ROOT}/C/Bcj2.c
    ${LZMA_ROOT}/C/Bra86.c
    ${LZMA_ROOT}/C/Bra.c
    ${LZMA_ROOT}/C/BraIA64.c
    ${LZMA_ROOT}/C/CpuArch.c
    ${LZMA_ROOT}/C/Delta.c
    ${LZMA_ROOT}/C/LzFind.c
    ${LZMA_ROOT}/C/Lzma2Dec.c
    ${LZMA_ROOT}/C/Lzma2Enc.c
    ${LZMA_ROOT}/C/Lzma86Dec.c
    ${LZMA_ROOT}/C/Lzma86Enc.c
    ${LZMA_ROOT}/C/LzmaDec.c
    ${LZMA_ROOT}/C/LzmaEnc.c
    ${LZMA_ROOT}/C/LzmaLib.c
    ${LZMA_ROOT}/C/Ppmd7.c
    ${LZMA_ROOT}/C/Ppmd7Dec.c
    ${LZMA_ROOT}/C/Ppmd7Enc.c
    ${LZMA_ROOT}/C/Sha256.c
    ${LZMA_ROOT}/C/Sort.c
    ${LZMA_ROOT}/C/Xz.c
    ${LZMA_ROOT}/C/XzCrc64.c
    ${LZMA_ROOT}/C/XzCrc64Opt.c
    ${LZMA_ROOT}/C/XzDec.c
    ${LZMA_ROOT}/C/XzEnc.c
    ${LZMA_ROOT}/C/XzIn.c
)
add_library(lzma STATIC ${LZMA_SOURCES})
