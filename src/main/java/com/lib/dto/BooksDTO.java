package com.lib.dto;

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

    private BooksAuthorDTO author;

    private BooksPublisherDTO publisher;

    private Integer publishDate;

    private BooksCategoryDTO category;

    private Set<ShowcaseImageDTO> image;

    private Boolean loanable;

    private String shelfCode;

    private Boolean active;

    private Boolean featured;

    private LocalDateTime createDate;

    private boolean builtIn;
}
