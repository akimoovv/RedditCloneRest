package com.springboot.reditclone.demo.mapper;


import com.springboot.reditclone.demo.dto.CommentsDto;
import com.springboot.reditclone.demo.exceptions.SpringRedditException;
import com.springboot.reditclone.demo.model.Comment;
import com.springboot.reditclone.demo.model.Post;
import com.springboot.reditclone.demo.model.User;
import com.springboot.reditclone.demo.repository.PostRepository;
import com.springboot.reditclone.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
public class CommentMapper {

    private final PostRepository postRepository;

    private final UserRepository userRepository;




    public Comment map(CommentsDto commentsDto) {

        Post post = postRepository.getOne(commentsDto.getPostId());

        User user = userRepository.findByUsername(commentsDto.getUserName())
                .orElseThrow(() -> new SpringRedditException("User hasn't benn found"));

        return Comment.builder()
                .id(commentsDto.getId())
                .post(post)
                .text(commentsDto.getText())
                .user(user)
                .build();
    }



    public CommentsDto mapToDto(Comment comment) {


        return CommentsDto.builder()
                .id(comment.getId())
                .postId(comment.getPost().getPostId())
                .text(comment.getText())
                .userName(comment.getUser().getUsername())
                .build();
    }

}
