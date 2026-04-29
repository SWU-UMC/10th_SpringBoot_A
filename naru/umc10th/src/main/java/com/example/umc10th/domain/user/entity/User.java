package com.example.umc10th.domain.user.entity;

import com.example.umc10th.domain.user.entity.enums.Gender;
import com.example.umc10th.domain.user.entity.enums.Role;
import com.example.umc10th.domain.user.entity.enums.SocialType;
import com.example.umc10th.domain.user.entity.enums.Status;
import com.example.umc10th.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(20)")
    private SocialType socialType;

    @Column(length = 255)
    private String socialId;

    @Column(nullable = false, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(20)")
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(20) DEFAULT 'ACTIVE'")
    private Status status;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer step; // 0: 가입완료, 1: 약관동의, 2: 정보입력, 3: 취향선택, 4: 완료

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer totalPoint;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(10)")
    private Gender gender;

    @Column(nullable = false)
    private LocalDate birth;

    @Column(length = 255)
    private String baseAddress;

    @Column(length = 255)
    private String detailAddress;

    @Column(length = 20)
    private String phoneNumber;

    @ColumnDefault("0")
    private Boolean isVerified;

    @Column(length = 500)
    private String profileImageKey;
}