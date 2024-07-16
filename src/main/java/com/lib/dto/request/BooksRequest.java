package com.lib.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BooksRequest {

    @NotBlank
    @Size(min = 2, max = 80, message = "Your book name ${validatedValue} must be between ${min} and ${max} characters.")
    private String name;

    @NotBlank
    @Size(min = 17, max = 17)
    @Pattern(regexp = "\\d{3}-\\d{2}-\\d{5}-\\d{2}-\\d")
    private String isbn;

    private Integer pageCount;

    @NotBlank(message = "You must provide a author")
    private Long authorId;

    @NotBlank(message = "You must provide a publisher")
    private Long publisherId;

    private Integer publishDate;

    @NotBlank(message = "You must provide a category")
    private Long categoryId;

    private Set<String> imageId;

    @NotBlank
    private Boolean loanable;

    @Size(min = 6, max = 6)
    @Pattern(regexp = "\\d{2}-\\d{3}")
    @NotBlank
    private String shelfCode;

    @NotBlank
    private Boolean active;

    @NotBlank
    private Boolean featured;


}
