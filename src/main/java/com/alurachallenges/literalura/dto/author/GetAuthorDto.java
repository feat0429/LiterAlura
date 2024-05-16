package com.alurachallenges.literalura.dto.author;

import java.util.List;

public record GetAuthorDto(
        String name,
        Integer birthYear,
        Integer deathYear,
        List<String> books
) {
}
