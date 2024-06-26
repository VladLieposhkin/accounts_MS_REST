package vl.example.accountsservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vl.example.accountsservice.entity.Account;
import vl.example.accountsservice.entity.Coin;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    @Query("SELECT a FROM Account a WHERE a.number = :number and (:id is NULL OR a.id <> :id)")
    Optional<Account> checkByNumberAndId(@Param("number") String accountNumber, @Param("id") Integer accountId);

    @Modifying
    @Query("UPDATE Account a SET a.price = :price, a.amount = a.quantity * :price WHERE a.coin = :coin")
    void updateAccountsByCoin(@Param("coin") Coin coin, @Param("price") Float price);

    @Query("select a from Account a left join a.coin as c where abs(c.change7d) > :threshold")
    List<Account> getAccountsByChangedCoins(@Param("threshold") Integer threshold);
}
