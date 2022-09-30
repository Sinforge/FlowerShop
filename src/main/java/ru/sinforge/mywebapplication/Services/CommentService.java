package ru.sinforge.mywebapplication.Services;

import org.springframework.stereotype.Service;
import ru.sinforge.mywebapplication.Entities.Comment;
import ru.sinforge.mywebapplication.Repositories.CommentRepo;
import ru.sinforge.mywebapplication.ViewModels.CommentViewModel;


@Service
public class CommentService {
    private final CommentRepo commentRepo;


    public CommentService(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }


    public void AddComment(CommentViewModel commentViewModel, String UserName) {
        Comment comment = new Comment();
        comment.setUsername(UserName);
        comment.setText(commentViewModel.getText());
        comment.setFlowerid(commentViewModel.getFlowerId());
        commentRepo.save(comment);
    }

    public Iterable<Comment> GetAllCommentsOnFlowerPage(String FlowerId) {
        return  commentRepo.findAllByFlowerid(FlowerId);
    }
}
