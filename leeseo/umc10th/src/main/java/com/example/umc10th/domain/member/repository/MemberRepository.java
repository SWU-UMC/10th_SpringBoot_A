package com.example.umc10th.domain.member.repository;

import com.example.umc10th.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findMemberById(Long id);
    @Query("""
        select m from Member m
        join fetch m.memberFoodList mf
        join fetch mf.food
        where m.id = :id
    """)
    Optional<Member> findMemberWithFoods(Long id);
}
