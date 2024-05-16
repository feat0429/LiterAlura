package com.alurachallenges.literalura.businesslogic.utility;

import com.alurachallenges.literalura.dto.ApiResponseDto;
import com.alurachallenges.literalura.dto.author.AuthorApiResponseDto;
import com.alurachallenges.literalura.dto.author.GetAuthorDto;
import com.alurachallenges.literalura.dto.book.GetBookDto;
import com.alurachallenges.literalura.dto.language.GetLanguageDto;
import com.alurachallenges.literalura.model.Author;
import com.alurachallenges.literalura.model.Book;
import com.alurachallenges.literalura.model.Language;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class Mapper {
    private final ObjectMapper objectMapper= new ObjectMapper();

    public <T> ApiResponseDto<T> mapApiResponse(String json, Class<T> classToMap) {
        var javaType = objectMapper
                .getTypeFactory()
                .constructParametricType(ApiResponseDto.class,classToMap);

        try {
            return objectMapper.readValue(json,javaType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public GetBookDto mapToGetBookDto(Book book){
        return  new GetBookDto(
                book.getTitle(),
                book.getAuthors().stream().map(Author::getName).toList(),
                book.getLanguages().stream().map(Language::getName).toList(),
                book.getDownloadCount()
        );
    }

    public Set<Author> mapToAuthors(List<AuthorApiResponseDto> authors){
        return authors.stream()
                .map(author -> new Author(author.name(),author.birthYear(),author.deathYear()))
                .collect(Collectors.toSet());
    }

    public Set<Language> mapToLanguages(List<String> languages){
        return languages.stream()
                .map(Language::new)
                .collect(Collectors.toSet());
    }

    public GetAuthorDto mapToGetAuthorDto(Author author){
        return  new GetAuthorDto(
                author.getName(),
                author.getBirthYear(),
                author.getDeathYear(),
                author.getBooks().stream().map(Book::getTitle).toList()
        );
    }
    public GetLanguageDto mapToGetLanguageDto(Language language){
        return  new GetLanguageDto(
                language.getName()
        );
    }
}
