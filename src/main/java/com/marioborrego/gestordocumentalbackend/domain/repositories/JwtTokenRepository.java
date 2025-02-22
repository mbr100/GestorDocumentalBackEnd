package com.marioborrego.gestordocumentalbackend.domain.repositories;

import com.marioborrego.gestordocumentalbackend.domain.models.JwtToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JwtTokenRepository extends JpaRepository<JwtToken, Long> {
    Optional<JwtToken> findByToken(String jwt);
}