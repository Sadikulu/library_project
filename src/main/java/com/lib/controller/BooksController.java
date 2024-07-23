package com.lib.controller;

import com.lib.dto.BooksDTO;
import com.lib.dto.request.BooksRequest;
import com.lib.dto.request.BooksUpdateRequest;
import com.lib.service.BooksService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BooksController {

    private final BooksService booksService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public  ResponseEntity<BooksDTO> createBrand(@Valid @RequestBody BooksRequest booksRequest){
        BooksDTO booksDTO = booksService.createBook(booksRequest);
        return ResponseEntity.ok(booksDTO);
    }

    @GetMapping
    public ResponseEntity<Page<BooksDTO>> getAllBooksWithPage(@RequestParam(value = "q",required = false) String query,
                                                                  @RequestParam(value = "categories",required = false) List<Long> categoryId,
                                                                  @RequestParam(value = "author",required = false) List<Long> authorId,
                                                                  @RequestParam(value = "publisher",required = false) List<Long> publisherId,
                                                                  @RequestParam("page") int page,
                                                                  @RequestParam("size") int size,
                                                                  @RequestParam("sort") String prop,
                                                                  @RequestParam(value = "direction",
                                                                    required = false,
                                                                    defaultValue = "DESC") Sort.Direction direction){
        Pageable pageable = PageRequest.of(page,size, Sort.by(direction,prop));
        Page<BooksDTO> productDTO = booksService.findAllWithQueryAndPage(query,categoryId,authorId,publisherId,pageable);
        return ResponseEntity.ok(productDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BooksDTO> getBooksById(@PathVariable("id") Long id){
        BooksDTO booksDTO=booksService.getBooksById(id);
        return ResponseEntity.ok(booksDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BooksDTO> updateBooksById(@Valid @RequestBody BooksUpdateRequest booksUpdateRequest, @PathVariable("id") Long id){
        BooksDTO booksDTO=booksService.updateBooksById(booksUpdateRequest,id);
        return ResponseEntity.ok(booksDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BooksDTO> updateBooksById(@PathVariable("id") Long id){
        BooksDTO booksDTO=booksService.deleteBooksById(id);
        return ResponseEntity.ok(booksDTO);
    }

}
