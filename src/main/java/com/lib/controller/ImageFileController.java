package com.lib.controller;

import com.lib.domain.ImageFile;
import com.lib.dto.ImageFileDTO;
import com.lib.service.ImageFileService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageFileController {

    private final ImageFileService imageFileService;

    @PostMapping("/upload")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        String imageId = imageFileService.saveImage(file);
        return ResponseEntity.ok(imageId);
    }


    // ********************* Download***************
    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String id) {
        ImageFile imageFile = imageFileService.getImageById(id);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + imageFile.getName())
                .body(imageFile.getImageData().getData());
    }

    // ***********Image Display***************
    @GetMapping("/display/{id}")
    public ResponseEntity<byte[]> displayFile(@PathVariable String id) {
        ImageFile imageFile = imageFileService.getImageById(id);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(imageFile.getImageData().getData(), header, HttpStatus.OK);
    }

//    //***********GetAllImages***********
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ImageFileDTO>> getAllImages(){
        List<ImageFileDTO> allImageDTO= imageFileService.getAllImages();
        return ResponseEntity.ok(allImageDTO);
    }

    //**********DeleteImages***********
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ImageFileDTO> deleteImageFile(@PathVariable String id){
        ImageFileDTO imageFileDTO=imageFileService.removeById(id);
        return ResponseEntity.ok(imageFileDTO);
    }
}
