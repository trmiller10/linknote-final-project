package com.tiyironyard;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by Taylor on 6/17/16.
 */
public interface TagRepository extends CrudRepository<Tag, Integer>{

    Tag findTagByTagName(String tagName);
}
