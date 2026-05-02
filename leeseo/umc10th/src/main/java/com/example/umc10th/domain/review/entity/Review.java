package com.example.umc10th.domain.review.entity;

import com.example.umc10th.domain.mission.enums.Address;
import com.example.umc10th.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "review")
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rate", nullable = false)
    private double rate;

    @Column(name = "content", nullable = false)
    private String content;

}
