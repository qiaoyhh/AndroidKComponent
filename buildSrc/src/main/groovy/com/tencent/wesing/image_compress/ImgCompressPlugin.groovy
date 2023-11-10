package com.tencent.wesing.image_compress

import org.gradle.api.Plugin
import org.gradle.api.Project

class ImgCompressPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println("ImgCompressPlugin  call " + project.name + "  gradle:" + project.gradle.toString() + " " + (project == project.getRootProject()))
        if (!project == project.getRootProject()) {
            throw new IllegalArgumentException("img-compress-plugin must works on project level gradle")
        }
        project.extensions.create(Constants.EXT_OPT, ImgCompressExtension)
        def imgTask = project.tasks.create("imgCompressTask", ImgCompressTask)
        imgTask.group = "image"
        imgTask.description = "Compress images"
    }
}
