package vl.example.accountsservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vl.example.accountsservice.entity.Client;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    @Query("SELECT c FROM Client c " +
            "LEFT JOIN FETCH c.accounts as a " +
            "LEFT JOIN FETCH a.coin " +
            "WHERE c.id = :clientId")
    Optional<Client> findByIdWithDetails(@Param("clientId") Integer clientId);

    boolean existsById(Integer clientId);

    @Query("SELECT c FROM Client c " +
            "WHERE c.email = :email AND (:id is NULL OR c.id <> :id)")
    Optional<Client> checkByEmailAndId(@Param("email") String email, @Param("id") Integer id);

    @Query("SELECT c FROM Client c " +
            "WHERE c.name = :name AND (:id is NULL OR c.id <> :id)")
    Optional<Client> checkByNameAndId(@Param("name") String name, @Param("id") Integer id);
}
