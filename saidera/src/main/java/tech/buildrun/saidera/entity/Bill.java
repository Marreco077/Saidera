package tech.buildrun.saidera.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name= "tb_bill")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "unique_id", nullable = false, unique = true)
    private String uniqueID = UUID.randomUUID().toString();

    @Column(name = "creator_name", nullable = false, length = 50)
    private String creatorName;

    @Column(name = "creation_time", nullable = false)
    private LocalDateTime creationTime = LocalDateTime.now();

    @Column(name = "expiration_time", nullable = false)
    private LocalDateTime expirationTime = creationTime.plusHours(24); // 24 hours reset user

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Foreign Key for User
    private User user;
 }
