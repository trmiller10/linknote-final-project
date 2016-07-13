package com.tiyironyard.services;

import com.tiyironyard.entities.Note;
import com.tiyironyard.entities.Tag;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

/**
 * Created by Taylor on 7/12/16.
 */
/*

@Repository
@Transactional
public class NoteSearch {
    @PersistenceContext
    private EntityManager entityManager;

    //@param tagName
    public List<Note> search(List<Tag> tagResults) {

        FullTextEntityManager fullTextEntityManager =
                Search.getFullTextEntityManager(entityManager);

        QueryBuilder queryBuilder =
                fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Note.class).get();

        Query query =
                queryBuilder.keyword().onFields("tags").matching(tagResults).createQuery();


        */
/**
         *         BooleanQuery query = new BooleanQuery();

         for(Tag tag : tagResults){
         query.add(tag.getTagName(), BooleanClause.Occur.MUST);
         }
         *//*

        FullTextQuery jpaQuery =
                fullTextEntityManager.createFullTextQuery(query, Note.class);

        @SuppressWarnings("unchecked")
        List<Note> noteResults = jpaQuery.getResultList();

        return noteResults;
    }
}
*/
