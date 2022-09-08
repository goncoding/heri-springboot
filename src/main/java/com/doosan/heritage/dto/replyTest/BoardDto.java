package com.doosan.heritage.dto.replyTest;

import com.doosan.heritage.model.replyTest.Member01;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoardDto {

    private Long bno;

    private String title;

    private String content;

    private String writerEmail;//작성자 이메일s

    private String writerName;//작성자 이름

    private LocalDateTime regDate;
    private LocalDateTime modeDate;
    private int replyCount; //게시글 댓글 수

}
