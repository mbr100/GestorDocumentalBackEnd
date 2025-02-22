package com.marioborrego.gestordocumentalbackend.domain.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter @Setter
public class JwtToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 2048)
    private String token;

    private Date expiration;

    private boolean isValid;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Usuario user;
}
