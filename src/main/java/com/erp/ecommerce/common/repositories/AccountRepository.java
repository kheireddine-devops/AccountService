package com.erp.ecommerce.common.repositories;

import com.erp.ecommerce.common.models.entities.Account;
import com.erp.ecommerce.common.models.enums.AccountStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID>, QuerydslPredicateExecutor<Account> /*, QuerydslBinderCustomizer<Account>*/ {
    Optional<Account> findByUsernameOrEmail(String username,String email);
    boolean existsAccountByUsername(String username);
    boolean existsAccountByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE Account account SET account.status = :accountStatus WHERE account.accountId= :accountId")
    int updateAccountStatus(@Param("accountId") UUID accountIs,@Param("accountStatus") AccountStatusEnum status);

    @Transactional
    @Modifying
    @Query("UPDATE Account account SET account.emailValidation.tempCodeValidateEmail = :verificationCode,account.emailValidation.ValidateEmail = false WHERE account.accountId= :accountId")
    int updateAccountTempCodeValidateEmail(@Param("accountId") UUID accountId,@Param("verificationCode") int verificationCode);

    @Query("SELECT account.email FROM Account account WHERE account.accountId = :accountId")
    String findEmailByAccountId(@Param("accountId") UUID accountId);
}
