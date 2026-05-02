package com.example.umc10th.domain.member.entity;

import com.example.umc10th.domain.member.enums.Gender;
import com.example.umc10th.domain.member.enums.SocialType;
import com.example.umc10th.domain.member.enums.Status;
import com.example.umc10th.domain.mission.enums.Address;
import com.example.umc10th.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nickanme", unique = true)
    private String nickname;

    @Builder.Default
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender = Gender.NONE;

    @Column(name = "birth")
    private LocalDate birth;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name = "address")
    @Enumerated(EnumType.STRING)
    private Address address;

    @Column(name = "full_address")
    private String fullAddress;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "social_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Column(name = "social_id", unique = true, nullable = false)
    private String socialId;

    @Builder.Default
    @Column(name = "point", nullable = false)
    private Integer point = 0;

    @Builder.Default
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVATE;

    @Builder.Default
    @Column(name = "is_owner", nullable = false)
    private Boolean isOwner = false;

}
