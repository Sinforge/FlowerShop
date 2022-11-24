package ru.sinforge.mywebapplication.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sinforge.mywebapplication.Entities.Comment;
import ru.sinforge.mywebapplication.Entities.User;


@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {
    Comment findCommentByFlowerid(Long flowerid);
    Iterable<Comment> findAllByFlowerid(Long flowerid);

}
