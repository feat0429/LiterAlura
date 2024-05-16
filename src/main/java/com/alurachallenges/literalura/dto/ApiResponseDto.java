package com.alurachallenges.literalura.dto;

import java.util.List;

public record ApiResponseDto<T>(
        Integer count,
        String next,
        String previous,
        List<T> results
) {
}
