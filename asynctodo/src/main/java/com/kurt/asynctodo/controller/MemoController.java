package com.kurt.asynctodo.controller;

import com.kurt.asynctodo.dto.request.MemoRequestDto;
import com.kurt.asynctodo.dto.response.MemoResponseDto;
import com.kurt.asynctodo.security.dto.MemberInfo;
import com.kurt.asynctodo.service.impl.MemoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/memos")
//Todo : memberInfo 어나니머스 들어올 위험 있음 AOP 처리!!
public class MemoController {

    private final MemoService memoService;

    @GetMapping("/{memo-id}")
    public ResponseEntity<MemoResponseDto> getMemo(@AuthenticationPrincipal MemberInfo memberInfo,
                                                   @PathVariable("memo-id") Long memoId) throws ExecutionException, InterruptedException {
        MemoResponseDto memoResponseDto = memoService.getById(memoId).get();

        return ResponseEntity.ok(memoResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<MemoResponseDto>> getMemoList(@AuthenticationPrincipal MemberInfo memberInfo) throws ExecutionException, InterruptedException {
        List<MemoResponseDto> memoResponseDtoList = memoService.getAll().get();

        return ResponseEntity.ok(memoResponseDtoList);
    }


    @PatchMapping
    public ResponseEntity<Void> updateMemo(@Valid @RequestBody MemoRequestDto memoRequestDto,
                           @AuthenticationPrincipal MemberInfo memberInfo) {
        memoService.updateById(memberInfo, memoRequestDto);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{memo-id}")
    public void deleteMemo(@AuthenticationPrincipal MemberInfo memberInfo,
                           @PathVariable("memo-id") Long memoId) {
        memoService.deleteById(memberInfo, memoId);
    }

    @PostMapping
    public ResponseEntity<MemoResponseDto> postMemo(@Valid @RequestBody MemoRequestDto memoRequestDto,
                         @AuthenticationPrincipal MemberInfo memberInfo) throws ExecutionException, InterruptedException {
        MemoResponseDto memoResponseDto = memoService.save(memberInfo, memoRequestDto).get();

        return ResponseEntity.created(URI.create("/api/v1/memos/" + memoResponseDto.id()))
                .body(memoResponseDto);
    }
}
