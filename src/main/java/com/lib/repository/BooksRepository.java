package com.lib.repository;

import com.lib.domain.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Books,Long> {

    Optional<Books> findBooksById(Long id);

    @Query("SELECT count(b) from Books b join b.image img where img.id=:id")
    Integer findBooksByImage_Id(@Param("id")String id);

    boolean existsByName(String name);
}
