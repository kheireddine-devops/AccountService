package com.erp.ecommerce.common.models.entities;

import com.erp.ecommerce.common.models.enums.AccountRoleEnum;
import com.erp.ecommerce.common.models.enums.AccountStatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @ToString
@Entity
public class Account extends Auditable<UUID> implements UserDetails, Serializable {
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
