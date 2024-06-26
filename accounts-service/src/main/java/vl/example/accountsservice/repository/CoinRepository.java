package vl.example.accountsservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vl.example.accountsservice.entity.Coin;

import java.util.Optional;

@Repository
public interface CoinRepository extends JpaRepository<Coin, Integer> {

    @Query("SELECT c FROM Coin c WHERE c.code = :code AND (:id is NULL OR c.id <> :id)")
    Optional<Coin> checkByCodeAndId(@Param("code") String code, @Param("id") Integer id);

    @Query("SELECT c FROM Coin c WHERE c.name = :name AND (:id is NULL OR c.id <> :id)")
    Optional<Coin> checkByNameAndId(@Param("name") String name, @Param("id") Integer id);

    boolean existsById(Integer coinId);

    Optional<Coin> findByCode(String code);
}
