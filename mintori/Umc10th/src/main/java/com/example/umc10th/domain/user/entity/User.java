package com.example.umc10th.domain.user.entity;

import com.example.umc10th.domain.user.enums.Gender;
import com.example.umc10th.global.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @Size(max = 255)
    @Column(name = "email", length = 255, unique = true)
    private String email;

    // 해시 기준으로 길이 여유
    @Size(max = 100)
    @Column(name = "password", length = 100)
    private String password;

    @NotBlank
    @Size(max = 50)
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 30)
    private Gender gender;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Size(max = 20)
    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Size(max = 255)
    @Column(name = "address", length = 255)
    private String address;

    @Size(max = 255)
    @Column(name = "detail_address", length = 255)
    private String detailAddress;

    @Builder.Default
    @Min(0)
    @Column(name = "point", nullable = false)
    private Long point = 0L;

    @Size(max = 512)
    @Column(name = "image_url", length = 512)
    private String imageUrl;


    @Column(name = "inactive_at")
    private LocalDateTime inactiveAt;
}