package com.tiyironyard.services;

import org.apache.catalina.LifecycleState;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.ReusableAnalyzerBase;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.util.Version;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.StringReader;
import java.util.List;

/**
 * Created by Taylor on 6/27/16.
 */




public class StopWordSearch {

}
/*
@Repository
@Transactional
public class StopWordSearch {

    @PersistenceContext
    private EntityManager entityManager;

    //@param tagName
    public List<Tag> searchStopWords(String tagName) {

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
*/
