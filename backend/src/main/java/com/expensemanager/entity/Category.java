package com.expensemanager.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Represents an expense category (e.g., Food, Transport, Entertainment).
 * If the user reference is null, it acts as a global default category available to all users.
 * If tied to a specific user, it's a custom category created by them.
 */
@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String icon;

    // null user_id means it's a default system category
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
