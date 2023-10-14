package com.wanted.controller;

import com.wanted.dto.image.ImageUrlResponse;
import com.wanted.enums.ImageDirectory;
import com.wanted.service.ImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/api/images")
@Api(tags = "이미지관련 API", description = "이미지 업로드/삭제 API")
@RestController
public class ImageController {

    private final ImageService imageService;

    @PostMapping
    @ApiOperation(
            value = "이미지 업로드",
            notes = "이미지를 업로드하고 URL을 반환합니다.(받은 URL을 JobPosting 생성 시에 사용하세요.)"
    )
    public ResponseEntity<ImageUrlResponse> uploadImage(
            MultipartFile image, ImageDirectory imageDirectory) throws Exception {

        String ImageUrl = imageService.saveFile(image, imageDirectory);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ImageUrlResponse.builder().imageUrl(ImageUrl).build());
    }
}
