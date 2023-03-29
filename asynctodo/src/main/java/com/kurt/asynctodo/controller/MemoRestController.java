package com.kurt.asynctodo.controller;

import com.kurt.asynctodo.service.impl.MemoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/memo")
public class MemoRestController {

    private final MemoService memoService;

//    @GetMapping
//    public void getMemoVault(){
//        memoService.getById(1L);
//
//    }
//
//    @GetMapping
//    public void getMemo(){
//        memoService.getById(1L);
//    }
//
//    @PatchMapping
//    public void updateMemo(){
//        memoService.getById(1L);
//    }
//
//    @DeleteMapping
//    public void deleteMemo(){
//        memoService.getById(1L);
//    }
//
//    @PostMapping
//    public void postMemo(){
//        memoService.getById(1L);
//    }
}
