package com.example.umc10th.domain.store.entity;

import com.example.umc10th.domain.member.entity.MemberPrefer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "food_category")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "catetory_id")
    private Long id;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "category")
    private List<Store> stores = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "category")
    private List<MemberPrefer> memberPrefers = new ArrayList<>();
}