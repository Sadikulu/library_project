package com.lib.domain;

import jakarta.persistence.*;
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
@Entity
@Table(name="t_books")
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 80, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String isbn;

    @Column
    private Integer pageCount;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @Column
    private Integer publishDate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "books_id")
    private Set<ImageFile> image;

    @Column
    private Boolean loanable=true;

    @Column
    private String shelfCode;

    @Column
    private Boolean active=true;

    @Column
    private Boolean featured=false;

    @Column
    private LocalDateTime createDate=LocalDateTime.now();

    @Column
    private boolean builtIn=false;


}
