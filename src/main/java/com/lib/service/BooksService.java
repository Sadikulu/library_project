package com.lib.service;

import com.lib.domain.*;
import com.lib.dto.BooksDTO;
import com.lib.dto.request.BooksRequest;
import com.lib.dto.request.BooksUpdateRequest;
import com.lib.exception.BadRequestException;
import com.lib.exception.ConflictException;
import com.lib.exception.ErrorMessage;
import com.lib.exception.ResourceNotFoundException;
import com.lib.mapper.BooksMapper;
import com.lib.repository.AuthorRepository;
import com.lib.repository.BooksRepository;
import com.lib.repository.CategoryRepository;
import com.lib.repository.PublisherRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BooksService {
    private final BooksRepository booksRepository;
    private final ImageFileService imageFileService;
    private final BooksMapper booksMapper;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final PublisherRepository publisherRepository;
    private final EntityManager entityManager;

//    public BooksService(BooksRepository booksRepository, ImageFileService imageFileService, BooksMapper booksMapper, AuthorRepository authorRepository, CategoryRepository categoryRepository, PublisherRepository publisherRepository) {
//        this.booksRepository = booksRepository;
//        this.imageFileService = imageFileService;
//        this.booksMapper = booksMapper;
//        this.authorRepository = authorRepository;
//        this.categoryRepository = categoryRepository;
//        this.publisherRepository = publisherRepository;
//    }

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
        ImageFile imageFile = imageFileService.getImageById(booksRequest.getImageId());
        if (imageFile == null) {
            throw new ResourceNotFoundException(ErrorMessage.IMAGE_NOT_FOUND_MESSAGE);
        }
        boolean usedImageExists=booksRepository.existsByImageId(imageFile.getId());
        if (usedImageExists){
            throw new ConflictException(ErrorMessage.IMAGE_USED_MESSAGE);
        }
//        Author author = authorRepository.findById(booksRequest.getAuthorId()).orElseThrow(
//                () -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, booksRequest.getAuthorId())));
//
//        Category category = categoryRepository.findById(booksRequest.getCategoryId()).orElseThrow(
//                () -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, booksRequest.getCategoryId())));
//
//        Publisher publisher = publisherRepository.findById(booksRequest.getPublisherId()).orElseThrow(
//                () -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, booksRequest.getPublisherId())));

        boolean usedBookExists = booksRepository.existsByName(getNamesWithFilter(booksRequest.getName()));
        if (usedBookExists) {
            throw new ConflictException(String.format(ErrorMessage.BOOK_CONFLICT_EXCEPTION, getNamesWithFilter(booksRequest.getName())));
        }
        Set<ImageFile> images = new HashSet<>();
        images.add(imageFile);
        Books books = booksMapper.booksRequestToBooks(booksRequest);
        books.setName(getNamesWithFilter(booksRequest.getName()));
//        books.setCategory(category);
//        books.setAuthor(author);
//        books.setPublisher(publisher);
        books.setImage(images);
        booksRepository.save(books);
        return booksMapper.booksToBooksDTO(books);
    }

    public BooksDTO getBooksById(Long id) {
        Books books=findBooksById(id);
        return booksMapper.booksToBooksDTO(books);
    }

//    public Page<BooksDTO> findAllWithQueryAndPage(String query, List<Long> categoryId, List<Long> authorId, List<Long> publisherId, Pageable pageable) {
//        Page<Books> booksPage = booksRepository.findAllWithQueryAndPage(query,categoryId,authorId,publisherId,pageable);
//        return booksPage.map(new Function<Books, BooksDTO>() {
//            @Override
//            public BooksDTO apply(Books books) {
//                return booksMapper.booksToBooksDTO(books);
//            }
//        });
//    }

    public PageImpl<BooksDTO> findAllWithQueryAndPage(String query, List<Long> categoryId, List<Long> authorId, List<Long> publisherId, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Books> criteriaQuery = cb.createQuery(Books.class);
        Root<Books> root = criteriaQuery.from(Books.class);
        List<Predicate> predicates = new ArrayList<>();

        if (query != null && !query.isEmpty()) {
            String likeSearchText = "%" + query.toLowerCase(Locale.US) + "%";
            Predicate searchByName = cb.like(cb.lower(root.get("name")), likeSearchText);
            predicates.add(cb.or(searchByName));
        }

        if (categoryId != null && !categoryId.isEmpty()) {
            predicates.add(root.get("category").get("id").in(categoryId));
        }

        if (authorId != null && !authorId.isEmpty()) {
            predicates.add(root.get("author").get("id").in(authorId));
        }

        if (publisherId != null && !publisherId.isEmpty()) {
            predicates.add(root.get("publisher").get("id").in(publisherId));
        }

        Predicate finalPredicate = cb.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.orderBy(pageable.getSort().stream()
                .map(order -> {
                    if (order.isAscending()) {
                        return cb.asc(root.get(order.getProperty()));
                    } else {
                        return cb.desc(root.get(order.getProperty()));
                    }
                })
                .collect(Collectors.toList()));
        criteriaQuery.where(finalPredicate);

        TypedQuery<Books> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult((int)pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cb.count(countQuery.from(Books.class)));
        countQuery.where(finalPredicate);
        Long totalRecords = entityManager.createQuery(countQuery).getSingleResult();
        List<BooksDTO> productDTOList = booksMapper.booksListToBooksDTOList(typedQuery.getResultList());

        return new PageImpl<>(productDTOList, pageable, totalRecords);
    }

    public BooksDTO updateBooksById(BooksUpdateRequest booksUpdateRequest, Long id) {
        Books books=findBooksById(id);
        if (books.isBuiltIn()) {
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }
        ImageFile imageFile=imageFileService.getImageById(booksUpdateRequest.getImageId());
        List<Books> bookList = booksRepository.findBooksByImageId(imageFile.getId());

        for (Books b : bookList) {
            if (books.getId().longValue()!=b.getId().longValue()) {
                throw new ConflictException(ErrorMessage.IMAGE_USED_MESSAGE);
            }
        }
//        Author author = authorRepository.findById(booksRequest.getAuthorId()).orElseThrow(
//                () -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, booksRequest.getAuthorId())));
//
//        Category category = categoryRepository.findById(booksRequest.getCategoryId()).orElseThrow(
//                () -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, booksRequest.getCategoryId())));
//
//        Publisher publisher = publisherRepository.findById(booksRequest.getPublisherId()).orElseThrow(
//                () -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, booksRequest.getPublisherId())));

        boolean usedBookExists = booksRepository.existsByName(getNamesWithFilter(booksUpdateRequest.getName()));
        if (usedBookExists) {
            throw new ConflictException(String.format(ErrorMessage.BOOK_CONFLICT_EXCEPTION, getNamesWithFilter(booksUpdateRequest.getName())));
        }
//        books.setPublisher(publisher);
//        books.setAuthor(author);
//        books.setCategory(category);
        books.setName(booksUpdateRequest.getName());
        books.setIsbn(booksUpdateRequest.getIsbn());
        books.setPageCount(booksUpdateRequest.getPageCount());
        books.setFeatured(booksUpdateRequest.getFeatured());
        books.setActive(booksUpdateRequest.getActive());
        books.setPublishDate(booksUpdateRequest.getPublishDate());
        books.setShelfCode(booksUpdateRequest.getShelfCode());
        books.getImage().add(imageFile);
        booksRepository.save(books);

        return booksMapper.booksToBooksDTO(books);
    }

    public BooksDTO deleteBooksById(Long id) {
        Books books=findBooksById(id);
        if(books.isBuiltIn()){
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }
        BooksDTO booksDTO=booksMapper.booksToBooksDTO(books);
        booksRepository.delete(books);
        return booksDTO;
    }
}
