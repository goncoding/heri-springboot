package com.doosan.heritage.model.replyTest;

import com.doosan.heritage.dto.base.BaseTimeEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Entity
public class Member01 extends BaseTimeEntity {

    @Id
    private String email;

    private String password;

    private String name;
}
