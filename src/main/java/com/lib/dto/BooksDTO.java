package com.lib.dto;

import com.lib.domain.Author;
import com.lib.domain.Category;
import com.lib.domain.Publisher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BooksDTO {

    private Long id;

    private String name;

    private String isbn;

    private Integer pageCount;

    private Author author;

    private Publisher publisher;

    private Integer publishDate;

    private Category category;

    private Set<String> image;

    private Boolean loanable;

    private String shelfCode;

    private Boolean active;

    private Boolean featured;

    private LocalDateTime createDate;

    private boolean builtIn;


}
