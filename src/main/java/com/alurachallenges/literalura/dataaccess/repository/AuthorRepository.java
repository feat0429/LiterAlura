package com.alurachallenges.literalura.dataaccess.repository;

import com.alurachallenges.literalura.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Set;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Set<Author> findByNameIn(Set<String> names);
    @NonNull
    List<Author> findAll();

    @Query("SELECT a FROM Author a WHERE :year >= a.birthYear AND :year < a.deathYear")
    List<Author> findLivingAuthors(Integer year);
}
