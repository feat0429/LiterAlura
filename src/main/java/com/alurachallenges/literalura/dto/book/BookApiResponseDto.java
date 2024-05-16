package com.alurachallenges.literalura.dto.book;

import com.alurachallenges.literalura.dto.author.AuthorApiResponseDto;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record BookApiResponseDto(
        String title,
        @JsonAlias("download_count")
        Integer downloadCount,
        List<String> languages,
        List<AuthorApiResponseDto> authors
) {
}
