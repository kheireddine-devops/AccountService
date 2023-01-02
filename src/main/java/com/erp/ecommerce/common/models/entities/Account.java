package com.erp.ecommerce.common.models.entities;

import com.erp.ecommerce.common.models.enums.AccountRoleEnum;
import com.erp.ecommerce.common.models.enums.AccountStatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @ToString
@Entity
public class Account implements Serializable {
    @Id
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(
//            name = "UUID",
//            strategy = "org.hibernate.id.UUIDGenerator"
//    )
    @GeneratedValue
    @Column(name = "account_id")
    private UUID accountId;

    @Column(length = 30, nullable = false, unique = true)
    private String username;

    /*
     * RFC-2821
     */
    @Column(length = 320, nullable = false, unique = true)
    private String email;

    @Column(length = 60, nullable = false)
    private String password;
    @Column(length = 11, nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountStatusEnum status;

    @Column(length = 21, nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountRoleEnum role;
}
