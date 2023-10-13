package com.wanted.model;

import com.wanted.enums.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //실제 로그인에 사용될 Id
    private String email;

    private String password;

    private String name;

    private String phone;

    //해당 필드는 기업의 경우에만 사용 (추후 서비스 확장가능성을 위해 - openApi 를 통한 기업 인증 등..)
    private String businessNumber;

    private String imageUrl;

    //회원의 타입을 나타내는 필드 (기업/구직자)
    @Enumerated(EnumType.STRING)
    private MemberRole role;
}
