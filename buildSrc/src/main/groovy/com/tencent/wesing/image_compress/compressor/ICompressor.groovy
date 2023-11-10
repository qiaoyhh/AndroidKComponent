package com.tencent.wesing.image_compress.compressor

import com.tencent.wesing.image_compress.CompressInfo
import com.tencent.wesing.image_compress.ImgCompressExtension
import com.tencent.wesing.image_compress.ResultInfo
import org.gradle.api.Project

/**
 * 压缩处理器抽象接口,有多种类型
 */
interface ICompressor {

    void compress(Project rootProject, List<CompressInfo> unCompressFileList, ImgCompressExtension config, ResultInfo resultInfo)
}