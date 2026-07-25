package com.cognizant.ormlearn.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cognizant.ormlearn.model.Country;

/**
 * Doc 3 - Hands on 6: Criteria Query.
 *
 * Mirrors the "online retail" scenario in the hands-on document: a user can
 * pick zero or more filter criteria (there it was review score, RAM, CPU...;
 * here we reuse the Country entity with "name contains" and "code equals"
 * filters) and the WHERE clause has to be built dynamically depending on
 * which filters were actually selected - which a fixed HQL/JPQL string
 * cannot do cleanly.
 */
@Service
public class CriteriaQueryDemo {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * @param filters supported keys: "nameContains", "codeEquals".
     *                Only the filters that are present are added to the query -
     *                this is the dynamic WHERE clause the hands-on asks about.
     */
    @Transactional
    public List<Country> search(Map<String, String> filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Country> query = cb.createQuery(Country.class);
        Root<Country> root = query.from(Country.class);

        List<Predicate> predicates = new ArrayList<>();

        if (filters.containsKey("nameContains")) {
            predicates.add(cb.like(root.get("name"), "%" + filters.get("nameContains") + "%"));
        }
        if (filters.containsKey("codeEquals")) {
            predicates.add(cb.equal(root.get("code"), filters.get("codeEquals")));
        }

        if (!predicates.isEmpty()) {
            query.where(predicates.toArray(new Predicate[0]));
        }

        return entityManager.createQuery(query).getResultList();
    }
}
