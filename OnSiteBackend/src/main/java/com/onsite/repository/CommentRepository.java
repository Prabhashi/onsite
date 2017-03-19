package com.onsite.repository;

import com.onsite.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by TJR on 3/13/2017.
 */
public interface CommentRepository extends JpaRepository<Comment,Integer> {


}
