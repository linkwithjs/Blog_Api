package com.rj.blog.repository;

import com.rj.blog.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    @Query(value="SELECT * from comments where blog_id=?1", nativeQuery = true)
    List<Comment> findByBlogId(Long id);
}
