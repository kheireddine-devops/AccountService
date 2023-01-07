package com.erp.ecommerce.commands.controllers.impls;

import com.erp.ecommerce.commands.controllers.specs.IAccountCommandsController;
import com.erp.ecommerce.common.actions.commands.AccountCommands;
import com.erp.ecommerce.common.dtos.mappers.AccountsMapper;
import com.erp.ecommerce.common.dtos.requests.AccountRequestsDTO;
import com.erp.ecommerce.common.dtos.requests.AuthRequestDTO;
import com.erp.ecommerce.common.dtos.responses.AuthResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping({"/accounts"})
@Log4j2
public class AccountCommandsController implements IAccountCommandsController {
    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtEncoder jwtEncoder;
    @Autowired
    private JwtDecoder jwtDecoder;
    @Autowired
    private AccountsMapper accountsMapper;

    @Value("${jwt.refresh-token.expiration}")
    private int JWT_REFRESH_TOKEN_EXPIRATION = 30;
    @Value("${jwt.access-token.expiration}")
    private int JWT_ACCESS_TOKEN_EXPIRATION = 5;

    @Override
    public CompletableFuture<String> addCustomer(AccountRequestsDTO.CreateCustomerAccountDTO createCustomerAccountDTO) {
        AccountCommands.CreateCustomerAccountCommand createCustomerAccountCommand = accountsMapper.fromAddCustomerAccountDTOToCreateCustomerAccountCommand(createCustomerAccountDTO);
        return this.commandGateway.send(createCustomerAccountCommand);
//        return this.commandGateway.sendAndWait(createCustomerAccountCommand,3, TimeUnit.SECONDS);
    }

    @Override
    public ResponseEntity<AuthResponseDTO> auth(Authentication authentication) {
        Instant instant = Instant.now();
        String scope = authentication.getAuthorities().stream().map(grantedAuthority -> grantedAuthority.getAuthority()).collect(Collectors.joining(","));
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .subject(authentication.getName())
                .issuedAt(instant)
                .expiresAt(instant.plus(30, ChronoUnit.DAYS))
                .issuer("EcommerceService")
                .claim("scope",scope)
                .build();

        String jwtAccessToken = this.jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();

        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        authResponseDTO.setValid(true);
        authResponseDTO.setAccessToken(jwtAccessToken);
        return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AuthResponseDTO> auth(AuthRequestDTO authRequestDTO) {
        Authentication authentication = null;
        String scope = null;
        String subject = null;

        switch (authRequestDTO.getGrantType()) {
            case PASSWORD:
                System.out.println("TOKEN-BY-PASSWORD");
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(),authRequestDTO.getPassword());
                authentication = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
                subject = authentication.getName();
                scope = authentication.getAuthorities().stream().map(grantedAuthority -> grantedAuthority.getAuthority()).collect(Collectors.joining(","));
                break;
            case REFRESH_TOKEN:
                System.out.println("TOKEN-BY-REFRESH_TOKEN");
                if(authRequestDTO.getRefreshToken() == null) {
                    return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
                }
                try {
                    Jwt refreshJWT = this.jwtDecoder.decode(authRequestDTO.getRefreshToken());
                    subject = refreshJWT.getSubject();
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(subject);
                    Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
                    scope = authorities.stream().map(grantedAuthority -> grantedAuthority.getAuthority()).collect(Collectors.joining(","));
                } catch (Exception exception) {
                    return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
                }

                break;
            default:
                System.out.println("PROBLEM");
                break;
        }

        Instant instant = Instant.now();
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .subject(scope)
                .issuedAt(instant)
                .expiresAt(instant.plus(authRequestDTO.isWithRefreshToken() ? JWT_ACCESS_TOKEN_EXPIRATION : JWT_REFRESH_TOKEN_EXPIRATION, ChronoUnit.MINUTES))
                .issuer("EcommerceService")
                .claim("scope",scope)
                .build();

        String jwtAccessToken = this.jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();

        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        authResponseDTO.setValid(true);
        authResponseDTO.setAccessToken(jwtAccessToken);

        if(authRequestDTO.isWithRefreshToken()) {
            JwtClaimsSet jwtClaimsSetRefreshToken = JwtClaimsSet.builder()
                    .subject(subject)
                    .issuedAt(instant)
                    .expiresAt(instant.plus(JWT_REFRESH_TOKEN_EXPIRATION, ChronoUnit.MINUTES))
                    .issuer("EcommerceService")
                    .build();

            String jwtRefreshToken = this.jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSetRefreshToken)).getTokenValue();
            authResponseDTO.setRefreshToken(jwtRefreshToken);
        }


        return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String,Object>> currenUser(Authentication authentication) {
        Map<String,Object> currentUser = Map.of(
                "username", authentication.getName(),
                "roles", authentication.getAuthorities()
        );
        return new ResponseEntity<>(currentUser,HttpStatus.OK);
    }
}
