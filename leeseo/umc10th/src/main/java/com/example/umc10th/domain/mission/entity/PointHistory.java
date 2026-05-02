package com.example.umc10th.domain.mission.entity;

import com.example.umc10th.domain.member.enums.FoodType;
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
@Table(name = "point_history")
public class PointHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rewards_point", nullable = false)
    private Integer rewardsPoint;

    @Column(name = "accumulation_date", nullable = false)
    private LocalDate accumulationDate;

    @Column(name = "total_point", nullable = false)
    private Integer totalPoint;
}
