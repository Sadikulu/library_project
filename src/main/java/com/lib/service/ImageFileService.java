package com.lib.service;

import com.lib.domain.ImageData;
import com.lib.domain.ImageFile;
import com.lib.dto.ImageFileDTO;
import com.lib.exception.ErrorMessage;
import com.lib.exception.ImageFileException;
import com.lib.exception.ResourceNotFoundException;
import com.lib.mapper.ImageFileMapper;
import com.lib.repository.ImageFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageFileService {

    private final ImageFileRepository imageFileRepository;
    private final ImageFileMapper imageFileMapper;

    public String saveImage(MultipartFile file) {
        ImageFile imageFile = null;
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            ImageData imData = new ImageData(file.getBytes());
            imageFile = new ImageFile(fileName, file.getContentType(), imData);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        imageFileRepository.save(imageFile);
        return imageFile.getId();
    }

    public ImageFile getImageById(String id) {
        return imageFileRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format(ErrorMessage.IMAGE_NOT_FOUND_MESSAGE, id)));

    }

    public List<ImageFileDTO> getAllImages() {
        List<ImageFile> imageFiles = imageFileRepository.findAll();
        return imageFiles.stream().map(imFile -> {
            String imageUri = ServletUriComponentsBuilder.
                    fromCurrentContextPath().
                    path("/files/download/").path(imFile.getId()).toUriString();
            return new ImageFileDTO(imFile.getName(), imageUri, imFile.getType());
        }).collect(Collectors.toList());
    }

    public ImageFileDTO removeById(String id) {
        ImageFile imageFile = getImageById(id);
        ImageFileDTO imageFileDTO=imageFileMapper.imageFileToImageFileDTO(imageFile);
        imageFileRepository.delete(imageFile);
        return imageFileDTO;
    }

}
