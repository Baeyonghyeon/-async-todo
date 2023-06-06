package com.kurt.asynctodo.service.impl;

import com.kurt.asynctodo.converter.MemoConverter;
import com.kurt.asynctodo.domain.Member;
import com.kurt.asynctodo.domain.Memo;
import com.kurt.asynctodo.dto.request.MemoRequestDto;
import com.kurt.asynctodo.dto.response.MemoResponseDto;
import com.kurt.asynctodo.repository.MemoRepository;
import com.kurt.asynctodo.config.security.dto.MemberInfo;
import com.kurt.asynctodo.service.CrudService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class MemoService implements CrudService<MemoRequestDto, MemoResponseDto> {

    private final MemoRepository memoRepository;
    private final MemoConverter memoConverter;

    @Transactional(readOnly = true)
    @Override
    @Async
    public CompletableFuture<MemoResponseDto> getById(final Long memoId) {
        Memo memo = memoRepository.findById(memoId)
                .orElseThrow(() -> new EntityNotFoundException("This note does not exist."));

        return CompletableFuture.completedFuture(memoConverter.MemoEntityToMomoResponseDto(memo));
    }

    @Transactional(readOnly = true)
    @Override
    @Async
    public CompletableFuture<List<MemoResponseDto>> getAll() {
        List<MemoResponseDto> memoResponseDtoList = memoRepository.findAll().stream()
                .map(memoConverter::MemoEntityToMomoResponseDto)
                .toList();

        // Todo : 페이징 적용
        return CompletableFuture.completedFuture(memoResponseDtoList);
    }

    @Override
    @Async
    public void updateById(final MemberInfo memberInfo, final MemoRequestDto requestDto, final Long memoId) {

        Memo memo = writtenByMeFindMemo(memberInfo, memoId);

        Optional.ofNullable(requestDto.title())
                .ifPresent(memo::updateTitle);
        Optional.ofNullable(requestDto.content())
                .ifPresent(memo::updateContent);
    }

    @Override
    @Async
    public CompletableFuture<MemoResponseDto> save(final MemberInfo memberInfo, final MemoRequestDto requestDto) {
        Memo memo = Memo.of(null, null, requestDto.title(), requestDto.content());
        memo.writer(new Member(memberInfo.memberId(), null, null, null, null));
        Memo save = memoRepository.save(memo);

        return CompletableFuture.completedFuture(memoConverter.MemoEntityToMomoResponseDto(save));
    }


    @Override
    @Async
    public void deleteById(final MemberInfo memberInfo, Long memoId) {
        Memo memo = writtenByMeFindMemo(memberInfo, memoId);

        memoRepository.delete(memo);
    }

    private Memo writtenByMeFindMemo(final MemberInfo memberInfo, final Long inquiryId) {
        return memoRepository.findById(inquiryId)
                .filter(a -> Objects.equals(a.getMember().getId(), memberInfo.memberId()))
                .orElseThrow(() -> new EntityNotFoundException("메모가 존재하지 않거나, 해당 유저가 작성하지 않음."));
    }
}
