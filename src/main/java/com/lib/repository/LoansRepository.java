package com.lib.repository;

import com.lib.domain.Loans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LoansRepository extends JpaRepository<Loans,Long> {

    @Query("Select l from Loans l join l.books book where book.id=:id")
    List<Loans> findLoansByBooksId(@Param("id") Long id);
}
