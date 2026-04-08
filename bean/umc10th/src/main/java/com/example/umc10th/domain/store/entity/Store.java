package com.example.umc10th.domain.store.entity;

import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.review.entity.Review;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "store")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private FoodCategory category;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "address", nullable = false, length = 255)
    private String address;

    @Column(name = "score", nullable = false)
    private Float score;

    @Builder.Default
    @OneToMany(mappedBy = "store")
    private List<Mission> missions = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "store")
    private List<Review> reviews = new ArrayList<>();
}
