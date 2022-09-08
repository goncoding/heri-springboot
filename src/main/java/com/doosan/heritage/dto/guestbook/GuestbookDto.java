package com.doosan.heritage.dto.guestbook;

import com.doosan.heritage.model.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class GuestbookDto {


    private Long gno;

    private String title;

    private String content;

    private String writer;

    private LocalDateTime regDate, modDate;

}
