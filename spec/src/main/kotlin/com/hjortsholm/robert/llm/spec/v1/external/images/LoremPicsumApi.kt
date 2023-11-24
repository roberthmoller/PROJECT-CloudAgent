package com.hjortsholm.robert.llm.spec.v1.external.images

import io.swagger.v3.oas.annotations.Operation
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "LoremPicsum", url = "https://picsum.photos")
interface LoremPicsumApi {
    @GetMapping("/{width}/{height}")
    @Operation(summary = "Get a random photo from Lorem Picsum")
    fun getRandomPhoto(
        @PathVariable width: Int,
        @PathVariable height: Int
    ): ByteArray
}

