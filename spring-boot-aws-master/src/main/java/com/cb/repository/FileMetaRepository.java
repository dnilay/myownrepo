package com.cb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cb.model.FileMeta;

@Repository
public interface FileMetaRepository extends CrudRepository<FileMeta, Integer> {
}
