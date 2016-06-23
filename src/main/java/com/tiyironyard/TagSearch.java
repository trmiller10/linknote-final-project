package com.tiyironyard;

import java.util.List;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Taylor on 6/23/16.
 */

@Repository
@Transactional
public class TagSearch {

    @PersistenceContext
    private EntityManager entityManager;

    //@param tagName
    public List<Tag> search(String tagName) {

        FullTextEntityManager fullTextEntityManager =
                org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);

        QueryBuilder queryBuilder =
                fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Tag.class).get();

        org.apache.lucene.search.Query query =
                queryBuilder.keyword().onFields("tagName").matching(tagName).createQuery();

        org.hibernate.search.jpa.FullTextQuery jpaQuery =
                fullTextEntityManager.createFullTextQuery(query, Tag.class);

        @SuppressWarnings("unchecked")
                List<Tag> tagResults = jpaQuery.getResultList();

        return tagResults;
    }

}
