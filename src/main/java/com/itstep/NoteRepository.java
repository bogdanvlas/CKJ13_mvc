package com.itstep;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//описывает функциональность NoteDao
//унаследовал базовые CRUD-методы от CrudRepository
@Repository
public interface NoteRepository extends CrudRepository<Note, Integer> {

}
