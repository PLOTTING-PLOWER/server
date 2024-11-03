package com.plotting.server.global.controller;

import com.plotting.server.global.application.S3Service;
import com.plotting.server.global.dto.request.ImageUploadTestRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "GlobalController", description = "전체 설정을 위해서 필요한 api")
@RequiredArgsConstructor
@RestController
@RequestMapping("/global")
public class GlobalController {

    private final S3Service s3Service;

    @Value("${server.env}")
    private String env;

    @Operation(summary = "health check", description = "ci/cd를 위한 health check api")
    @GetMapping("/health-check")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(env);
    }

    @Operation(summary = "사진 업로드 테스트", description = "사진 업로드 테스트")
    @PostMapping(value = "/upload-test", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> uploadTest(
            @RequestPart MultipartFile file,
            @RequestPart ImageUploadTestRequest request
    ) {

        s3Service.uploadFile(file, request.folderName());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("upload test");
    }

    @Operation(summary = "사진 삭제 테스트", description = "사진 삭제 테스트")
    @DeleteMapping(value = "/delete-test")
    public ResponseEntity<String> deleteTest(
            @RequestParam String str
    ) {

        s3Service.deleteFile(str);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("delete test");
    }
}