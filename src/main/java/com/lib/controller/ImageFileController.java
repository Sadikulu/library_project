package com.lib.controller;

import com.lib.domain.ImageFile;
import com.lib.dto.ImageFileDTO;
import com.lib.service.ImageFileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/image")
@AllArgsConstructor
public class ImageFileController {

    private ImageFileService imageFileService;

    @PostMapping("/upload")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<Set<String>> uploadFile(@RequestParam("image") MultipartFile[] image){
       Set<String> images = imageFileService.saveImage(image);
       return ResponseEntity.ok(images);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> getImageFile(@PathVariable String id){
        ImageFile imageFile=imageFileService.getImageById(id);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename="+imageFile.getName()).body(imageFile.getImageData().getData());
    }

    @GetMapping("/display/{id}")
    public ResponseEntity<byte[]>displayImage(@PathVariable String id){
        ImageFile imageFile= imageFileService.getImageById(id);
        HttpHeaders header=new HttpHeaders();
        header.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(imageFile.getImageData().getData(), header, HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ImageFileDTO>> getAllImages(){
        List<ImageFileDTO> imageList=imageFileService.getAllImages();
        return ResponseEntity.ok(imageList);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ImageFileDTO>deleteImageFile(@PathVariable String id){
        ImageFileDTO imageFileDTO= imageFileService.removeById(id);
                return ResponseEntity.ok(imageFileDTO);
    }
}
