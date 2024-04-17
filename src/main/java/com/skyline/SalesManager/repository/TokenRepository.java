package com.skyline.SalesManager.repository;

import com.skyline.SalesManager.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query("""
            select t from Token t join UserEntity u on t.userEntity.idUser = u.idUser
            where u.idUser = :idUser and (t.expored = false or t.revoked = false)
            """)
    List<Token> findAllValidTokenByUser(Long idUser);

    Optional<Token> findByToken(String token);
}
