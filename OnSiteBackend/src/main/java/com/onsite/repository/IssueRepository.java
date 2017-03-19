package com.onsite.repository;

import com.onsite.model.Issue;
import com.onsite.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by TJR on 1/27/2017.
 */
public interface IssueRepository extends JpaRepository<Issue,Integer> {
    List<Issue> findByProjectProjectId(Integer projectId);
    List<Issue> findAll();

    List<Issue> findByReporter(User user);

    List<Issue> findByAssignee(User user);
}
