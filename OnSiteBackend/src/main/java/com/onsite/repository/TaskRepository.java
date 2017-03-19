package com.onsite.repository;

import com.onsite.model.Task;
import com.onsite.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by TJR on 3/10/2017.
 */

public interface TaskRepository extends JpaRepository<Task, Integer> {
    public List<Task> findByProjectProjectId(Integer projectId);

    List<Task> findByAssignee(User user);
}
