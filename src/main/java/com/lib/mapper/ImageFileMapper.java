package com.lib.mapper;

import com.lib.domain.ImageFile;
import com.lib.dto.ImageFileDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageFileMapper {

    ImageFileDTO imageFileToImageFileDTO(ImageFile imageFile);
}
