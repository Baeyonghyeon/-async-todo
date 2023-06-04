package com.kurt.asynctodo.converter;

import com.kurt.asynctodo.domain.Member;
import com.kurt.asynctodo.domain.Memo;
import com.kurt.asynctodo.dto.request.MemoRequestDto;
import com.kurt.asynctodo.dto.response.MemoResponseDto;
import org.springframework.stereotype.Component;

@Component
public interface MemoConverter {

    default Memo MemoRequestDtoToMemoEntity(MemoRequestDto memoRequestDto, Long memberId){
        return Memo.of(
                null,
                Member.of(memberId),
                memoRequestDto.title(),
                memoRequestDto.content()
        );
    }

    default MemoResponseDto MemoEntityToMomoResponseDto(Memo memo){
        return MemoResponseDto.of(
                memo.getId(),
                memo.getMember(),
                memo.getTitle(),
                memo.getContent(),
                memo.getCreatedAt(),
                memo.getModifiedAt(),
                memo.getCreatedBy(),
                memo.getModifiedBy()
        );
    }
}
