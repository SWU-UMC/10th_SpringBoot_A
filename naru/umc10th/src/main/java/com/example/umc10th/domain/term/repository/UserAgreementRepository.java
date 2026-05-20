package com.example.umc10th.domain.term.repository;

import com.example.umc10th.domain.term.entity.mapping.UserAgreement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAgreementRepository extends JpaRepository<UserAgreement, Long> {
}
