package com.lib.service;

import com.lib.domain.*;
import com.lib.dto.BooksDTO;
import com.lib.dto.request.BooksRequest;
import com.lib.exception.ConflictException;
import com.lib.exception.ErrorMessage;
import com.lib.exception.ResourceNotFoundException;
import com.lib.mapper.BooksMapper;
import com.lib.repository.AuthorRepository;
import com.lib.repository.BooksRepository;
import com.lib.repository.CategoryRepository;
import com.lib.repository.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Service
public class BooksService {
    private final BooksRepository booksRepository;
    private final ImageFileService imageFileService;
    private final BooksMapper booksMapper;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final PublisherRepository publisherRepository;

    public BooksService(BooksRepository booksRepository, ImageFileService imageFileService, BooksMapper booksMapper, AuthorRepository authorRepository, CategoryRepository categoryRepository, PublisherRepository publisherRepository) {
        this.booksRepository = booksRepository;
        this.imageFileService = imageFileService;
        this.booksMapper = booksMapper;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.publisherRepository = publisherRepository;
    }

    public String getNamesWithFilter(String word){
        StringBuilder sb = new StringBuilder();
        String[] words = word.split(" ");
        if (words.length>1){
            for (String each : words) {
                if (each.length()>1) {
                    sb.append(each.substring(0, 1).toUpperCase());
                    sb.append(each.substring(1).toLowerCase());
                    sb.append(" ");
                }else{
                    sb.append(each.toUpperCase(Locale.forLanguageTag("en-US")));
                    sb.append(" ");
                }
            }
        }else{
            if (words[0].length()>1){
                sb.append(words[0].substring(0, 1).toUpperCase());
                sb.append(words[0].substring(1).toLowerCase());
            }else{
                sb.append(words[0].toUpperCase(Locale.forLanguageTag("en-US")));
            }
        }
        return sb.toString().trim();
    }

    public Books findBooksById(Long id) {
        return booksRepository.findBooksById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));
    }

    public BooksDTO createBook(BooksRequest booksRequest) {
        ImageFile imageFile = imageFileService.getImageById(booksRequest.getImageId().stream().findFirst().orElse(null));

        Author author = authorRepository.findById(booksRequest.getAuthorId()).orElseThrow(
                () -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, booksRequest.getAuthorId())));

        Category category = categoryRepository.findById(booksRequest.getCategoryId()).orElseThrow(
                () -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, booksRequest.getCategoryId())));

        Publisher publisher = publisherRepository.findById(booksRequest.getPublisherId()).orElseThrow(
                () -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, booksRequest.getPublisherId())));

        if (imageFile == null) {
            throw new ResourceNotFoundException(ErrorMessage.IMAGE_NOT_FOUND_MESSAGE);
        }

        boolean usedBookExists = booksRepository.existsByName(getNamesWithFilter(booksRequest.getName()));
        if (usedBookExists) {
            throw new ConflictException(String.format(ErrorMessage.BOOK_CONFLICT_EXCEPTION, getNamesWithFilter(booksRequest.getName())));
        }

        Set<ImageFile> images = new HashSet<>();
        images.add(imageFile);

        Books books = booksMapper.booksRequestToBooks(booksRequest);
        books.setName(getNamesWithFilter(booksRequest.getName()));
        books.setCategory(category);
        books.setAuthor(author);
        books.setPublisher(publisher);
        books.setImage(images);

        booksRepository.save(books);

        return booksMapper.booksToBooksDTO(books);
    }


    public BooksDTO getBooksById(Long id) {
        Books books=findBooksById(id);
        return booksMapper.booksToBooksDTO(books);
    }


}
