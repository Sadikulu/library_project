package com.lib.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BooksUpdateRequest {

    @NotBlank
    @Size(min = 2, max = 80, message = "Your book name ${validatedValue} must be between ${min} and ${max} characters.")
    private String name;

    @NotBlank
    @Size(min = 17, max = 17)
    @Pattern(regexp = "\\d{3}-\\d{2}-\\d{5}-\\d{2}-\\d")
    private String isbn;

    private Integer pageCount;

//    @NotBlank(message = "You must provide a author")
//    private Long authorId;
//
//    @NotBlank(message = "You must provide a publisher")
//    private Long publisherId;

    private Integer publishDate;

//    @NotBlank(message = "You must provide a category")
//    private Long categoryId;

    private String imageId;

    @Size(min = 6, max = 6)
    @Pattern(regexp = "[A-Z]{2}-\\d{3}")
    @NotBlank
    private String shelfCode;

    @NotNull
    private Boolean active;

    @NotNull
    private Boolean featured;

}
