package com.lib.controller;

import com.lib.dto.BooksDTO;
import com.lib.dto.request.BooksRequest;
import com.lib.service.BooksService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
//@RequiredArgsConstructor
public class BooksController {

    private final BooksService booksService;

    public BooksController(BooksService booksService) {
        this.booksService = booksService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public  ResponseEntity<BooksDTO> createBrand(@Valid @RequestBody BooksRequest booksRequest){
        BooksDTO booksDTO = booksService.createBook(booksRequest);
        return ResponseEntity.ok(booksDTO);
    }

//    @GetMapping
//    public ResponseEntity<PageImpl<BooksDTO>> getAllBooksWithPage(@RequestParam(value = "q",required = false) String query,
//                                                          @RequestParam(value = "categories",required = false) List<Long> categoryId,
//                                                          @RequestParam(value = "author",required = false) List<Long> authorId,
//                                                          @RequestParam(value = "publisher",required = false) List<Long> publisherId,
//                                                          @RequestParam("page") int page,
//                                                          @RequestParam("size") int size,
//                                                          @RequestParam("sort") String prop,
//                                                          @RequestParam(value = "direction",
//                                                                    required = false,
//                                                                    defaultValue = "DESC") Sort.Direction direction){
//        Pageable pageable = PageRequest.of(page,size, Sort.by(direction,prop));
//        PageImpl<BooksDTO> productDTO = booksService.findAllWithQueryAndPage(query,categoryId,authorId,publisherId,pageable);
//        return ResponseEntity.ok(productDTO);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<BooksDTO> getBooksById(@PathVariable("id") Long id){
        BooksDTO booksDTO=booksService.getBooksById(id);
        return ResponseEntity.ok(booksDTO);
    }

}
