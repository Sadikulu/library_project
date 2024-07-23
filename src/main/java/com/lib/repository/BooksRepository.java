package com.lib.repository;

import com.lib.domain.Books;
import com.lib.domain.ImageFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface BooksRepository extends JpaRepository<Books,Long> {

    Optional<Books> findBooksById(Long id);

    @Query("SELECT count(b) from Books b join b.image img where img.id=:id")
    Integer findBooksByImage_Id(@Param("id")String id);

    boolean existsByName(String name);

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM Books b JOIN b.image img WHERE img.id = :imageId")
    boolean existsByImageId(@Param("imageId") String imageId);


    @Query("Select b from Books b join b.image im where im.id=:id")
    List<Books> findBooksByImageId(@Param("id") String id);
}
