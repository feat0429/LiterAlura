package com.alurachallenges.literalura.businesslogic.services;

import com.alurachallenges.literalura.businesslogic.utility.Mapper;
import com.alurachallenges.literalura.dataaccess.repository.AuthorRepository;
import com.alurachallenges.literalura.dto.author.GetAuthorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final Mapper mapper;

    @Autowired
    public AuthorService(AuthorRepository authorRepository, Mapper mapper) {
        this.authorRepository = authorRepository;
        this.mapper = mapper;
    }

    public List<GetAuthorDto> getStoredAuthors(){
        return authorRepository.findAll().stream()
                .map(mapper::mapToGetAuthorDto)
                .toList();
    }

    public List<GetAuthorDto> getLivingAuthors(Integer year){
        return authorRepository.findLivingAuthors(year).stream()
                .map(mapper::mapToGetAuthorDto)
                .toList();
    }
}
