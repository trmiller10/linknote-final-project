package com.tiyironyard.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tiyironyard.entities.Note;
import com.tiyironyard.entities.Tag;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.search.jpa.Search;

/**
 * Created by Taylor on 6/23/16.
 */

@Repository
@Transactional
public class TagSearch {

    @PersistenceContext
    private EntityManager entityManager;

    //@param tagName
    public List<Tag> search(String searchResult) {

        FullTextEntityManager fullTextEntityManager =
                Search.getFullTextEntityManager(entityManager);

        QueryBuilder queryBuilder =
                fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Tag.class).get();

        Query query =
                queryBuilder.keyword().onFields("tagName").matching(searchResult).createQuery();

        FullTextQuery jpaQuery =
                fullTextEntityManager.createFullTextQuery(query, Tag.class);

        @SuppressWarnings("unchecked")
        List<Tag> resultList = jpaQuery.getResultList();
        Set<Tag> tagSet = new HashSet<>();
        tagSet.addAll(resultList);
        List<Tag> tagResults = new ArrayList<>();
        tagResults.addAll(tagSet);

        return tagResults;
    }

}
