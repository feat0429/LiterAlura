package com.alurachallenges.literalura.dataaccess.repository;

import com.alurachallenges.literalura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByTitle(String bookTitle);
    Book findByTitle(String bookTitle);
    @NonNull
    List<Book> findAll();
    @Query("SELECT b FROM Book b ORDER BY b.downloadCount DESC LIMIT 10")
    List<Book> findTop10MostDownloadedBooks();
    @Query("SELECT b FROM Book b JOIN b.languages l WHERE l.name = :language")
    List<Book> findByLanguage(String language);
    @Query("SELECT b FROM Book b JOIN b.authors a WHERE LOWER(a.name) LIKE CONCAT('%',:authorName,'%')")
    List<Book> findByAuthor(String authorName);
}
