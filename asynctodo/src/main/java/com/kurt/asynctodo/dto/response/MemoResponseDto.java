package com.kurt.asynctodo.dto.response;

import com.kurt.asynctodo.domain.Member;

import java.time.LocalDateTime;

public record MemoResponseDto(
        Long id,
        String memberName,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt,
        String createdBy,
        String modifiedBy
) {

    public static MemoResponseDto of(Long id, Member member, String title, String content, LocalDateTime createdAt, LocalDateTime modifiedAt, String createdBy, String modifiedBy) {
        return new MemoResponseDto(
                id,
                member.getUsername(),
                title,
                content,
                createdAt,
                modifiedAt,
                createdBy,
                modifiedBy
        );
    }
}
