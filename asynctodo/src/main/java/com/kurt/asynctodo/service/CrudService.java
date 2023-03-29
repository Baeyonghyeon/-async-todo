package com.kurt.asynctodo.service;

import com.kurt.asynctodo.domain.Memo;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CrudService<T> {

    CompletableFuture<T> getById(final Long id);

    CompletableFuture<List<T>> getAll();

    void updateById(final Long id);

    CompletableFuture<T> save(final T object);

    void deleteById(final Long id);
}