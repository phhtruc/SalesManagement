package com.skyline.SalesManager.repository;

import com.skyline.SalesManager.token.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query("""
            select t from Token t join UserEntity u on t.userEntity.id_user = u.id_user
            where u.id_user = :idUser and (t.expored = false or t.revoked = false)
            """)
    List<Token> findAllValidTokenByUser(Long idUser);

    Optional<Token> findByToken(String token);
}
