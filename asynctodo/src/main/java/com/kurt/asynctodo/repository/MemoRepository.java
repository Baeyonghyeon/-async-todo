package com.kurt.asynctodo.repository;

import com.kurt.asynctodo.domain.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo, Long> {
}