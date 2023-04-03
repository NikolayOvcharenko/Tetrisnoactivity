package com.example.tetrisnoactivity.helper

// class HelperFunctions {
    fun array2dOfByte(sizeOuter: Int, sizeInner: Int): Array<ByteArray>
        = Array(sizeOuter){ByteArray(sizeInner)}
//}