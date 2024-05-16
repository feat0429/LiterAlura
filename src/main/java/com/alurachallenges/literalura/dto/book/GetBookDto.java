package com.alurachallenges.literalura.dto.book;

import java.util.List;

public record GetBookDto(
        String title,
        List<String> authors,
        List<String> languages,
        Integer downloadCount
) { }
