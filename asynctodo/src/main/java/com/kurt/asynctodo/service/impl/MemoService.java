package com.kurt.asynctodo.service.impl;

import com.kurt.asynctodo.domain.Memo;
import com.kurt.asynctodo.repository.MemoRepository;
import com.kurt.asynctodo.service.CrudService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class MemoService implements CrudService<Memo> {

    private final MemoRepository memoRepository;

    @Transactional(readOnly = true)
    @Override
    @Async
    public CompletableFuture<Memo> getById(final Long id) {
        return CompletableFuture.completedFuture(null);
    }

    @Transactional(readOnly = true)
    @Override
    @Async
    public CompletableFuture<List<Memo>> getAll() {
        return CompletableFuture.supplyAsync(memoRepository::findAll);
    }

    @Override
    @Async
    public void updateById(final Long id) {
        // TODO document why this method is empty
    }

    @Override
    @Async
    public CompletableFuture<Memo> save(final Memo object) {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    @Async
    public void deleteById(Long id) {
        // TODO document why this method is empty
    }
}
