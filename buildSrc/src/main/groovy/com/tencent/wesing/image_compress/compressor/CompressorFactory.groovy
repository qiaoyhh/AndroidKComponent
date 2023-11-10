package com.tencent.wesing.image_compress.compressor

import com.tencent.wesing.image_compress.Constants
import com.tencent.wesing.image_compress.util.Logger
import org.gradle.api.Project

class CompressorFactory {

    static ICompressor getCompressor(String way, Project project) {
        switch (way) {
            case Constants.WAY_TINY:
                return new TinyCompressor()
                break;
            case Constants.WAY_QUANT:
                return new PngquantCompressor()
                break;
            case Constants.WAY_ZOPFLIP:
                return new ZopflipngCompressor()
                break;
            default:
                String errorMsg = "imgCompressOpt field 'way' error ,way must setting with one of [${Constants.WAY_TINY} ,${Constants.WAY_QUANT},${Constants.WAY_ZOPFLIP}]"
                Logger.getInstance(project.rootProject).e(errorMsg)
                throw new IllegalArgumentException(errorMsg)
                break
        }
    }

}