package com.example.umc10th.domain.mission.entity;

import com.example.umc10th.domain.member.enums.FoodType;
import com.example.umc10th.domain.mission.enums.Address;
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
@Table(name = "store")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "store_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private FoodType StoreType;

    @Column(name = "address", nullable = false)
    @Enumerated(EnumType.STRING)
    private Address address;

    @Column(name = "full_address")
    private String fullAddress;

}
