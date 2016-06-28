package com.tiyironyard.repositories;

import com.tiyironyard.entities.Note;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Taylor on 6/16/16.
 */
public interface NoteRepository extends CrudRepository<Note, Integer> {
    Note findByIdAndUserId(Integer id, Integer userId);

}
