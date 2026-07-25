package com.cognizant.ormlearn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cognizant.ormlearn.model.Attempt;

/**
 * Doc 3 - Hands on 3: Fetch quiz attempt details using HQL.
 *
 * Joins, in the order requested by the hands-on: user -> attempt ->
 * attempt_question -> question -> attempt_option -> options, with "fetch"
 * on every one-to-many / many-to-many hop so everything loads in one query.
 */
@Repository
public interface AttemptRepository extends JpaRepository<Attempt, Integer> {

    @Query("SELECT DISTINCT a FROM Attempt a "
            + "left join fetch a.user u "
            + "left join fetch a.attemptQuestionList aq "
            + "left join fetch aq.question q "
            + "left join fetch aq.attemptOptionList ao "
            + "left join fetch ao.option o "
            + "WHERE u.id = :userId AND a.id = :attemptId")
    Attempt getAttempt(@Param("userId") int userId, @Param("attemptId") int attemptId);
}
