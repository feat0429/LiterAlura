package com.alurachallenges.literalura.businesslogic.services;

import com.alurachallenges.literalura.businesslogic.utility.Mapper;
import com.alurachallenges.literalura.dataaccess.repository.LanguageRepository;
import com.alurachallenges.literalura.dto.language.GetLanguageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageService {
    private final LanguageRepository languageRepository;
    private final Mapper mapper;

    @Autowired
    public LanguageService(LanguageRepository languageRepository, Mapper mapper) {
        this.languageRepository = languageRepository;
        this.mapper = mapper;
    }

    public List<GetLanguageDto> getAllLanguages(){
        return languageRepository.findAll().stream()
                .map(mapper::mapToGetLanguageDto)
                .toList();
    }
}
