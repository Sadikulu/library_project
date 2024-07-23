package com.lib.mapper;

import com.lib.domain.Books;
import com.lib.domain.ImageFile;
import com.lib.dto.BooksDTO;
import com.lib.dto.request.BooksRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BooksMapper {

    @Mapping(source = "image", target = "image", qualifiedByName = "getImageAsString")
    BooksDTO booksToBooksDTO(Books books);

    Books booksRequestToBooks(BooksRequest booksRequest);

    List<BooksDTO> booksListToBooksDTOList(List<Books> booksList);

    @Named("getImageAsString")
    public static Set<String> getImageAsString(Set<ImageFile> images) {
        if (images == null) {
            return null;
        }
        return images.stream()
                .map(ImageFile::getId)
                .collect(Collectors.toSet());
    }
}
