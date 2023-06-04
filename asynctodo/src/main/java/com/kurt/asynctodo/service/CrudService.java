package com.kurt.asynctodo.service;

import com.kurt.asynctodo.security.dto.MemberInfo;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CrudService<T,S> {

    CompletableFuture<S> getById(final Long id);

    CompletableFuture<List<S>> getAll();

    void updateById(final MemberInfo memberInfo, final T requestDto);

    CompletableFuture<S> save(final MemberInfo memberInfo, final T requestDto);

    void deleteById(final MemberInfo memberInfo, final Long id);
}