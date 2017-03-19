package com.onsite.repository;

import com.onsite.model.Project;
import com.onsite.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by TJR on 1/26/2017.
 */
public interface ProjectRepository extends JpaRepository<Project, Integer>{
    Project findOne(Integer id);

    List<Project> findByManager(User user);

    List<Project> findByClient(User user);
}
