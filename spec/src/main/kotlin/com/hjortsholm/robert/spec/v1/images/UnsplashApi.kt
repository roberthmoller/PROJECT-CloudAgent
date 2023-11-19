package com.hjortsholm.robert.spec.v1.images

import io.swagger.v3.oas.annotations.Operation
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "Unsplash", url = "https://source.unsplash.com")
interface UnsplashApi {
    @GetMapping("/random", produces = ["image/jpeg"])
    @Operation(summary = "Get a random photo of any size from Unsplash")
    fun getRandomPhoto(): ByteArray

    @GetMapping("/random/{width}*{height}", produces = ["image/jpeg"])
    @Operation(summary = "Get a random photo with a given size from Unsplash")
    fun getRandomPhotoWithSize(
        @PathVariable width: String,
        @PathVariable height: String,
    ): ByteArray

    @GetMapping("/random?{query}", produces = ["image/jpeg"])
    @Operation(summary = "Get an image of a given subject with any size from Unsplash")
    fun getPhotoOfSubject(@PathVariable query: String): ByteArray

    @GetMapping("/random/{width}*{height}/?{query}", produces = ["image/jpeg"])
    @Operation(summary = "Get an image of a given subject with a given size from Unsplash")
    fun getPhotoOfSubjectWithSize(
        @PathVariable query: String,
        @PathVariable width: String,
        @PathVariable height: String,
    ): ByteArray
}