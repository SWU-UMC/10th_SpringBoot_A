package com.example.umc10th.domain.notification.entity;

import com.example.umc10th.domain.notification.entity.enums.NotificationTargetType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "notification_category")
public class NotificationCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 255)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", columnDefinition = "VARCHAR(20)")
    private NotificationTargetType targetType;
}
