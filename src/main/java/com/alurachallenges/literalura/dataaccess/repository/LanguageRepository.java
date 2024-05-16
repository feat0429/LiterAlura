package com.alurachallenges.literalura.dataaccess.repository;

import com.alurachallenges.literalura.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {
    Set<Language> findByNameIn(Set<String> names);

    @NonNull
    List<Language> findAll();
}
