package com.erp.ecommerce.commands.controllers.impls;

import com.erp.ecommerce.commands.controllers.specs.IAuthController;
import com.erp.ecommerce.common.configs.security.JwtUtils;
import com.erp.ecommerce.common.dtos.requests.AuthRequestDTO;
import com.erp.ecommerce.common.dtos.responses.AuthResponseDTO;
import com.erp.ecommerce.common.models.entities.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping({"/accounts"})
@RequiredArgsConstructor
public class AuthController implements IAuthController {


    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;
    @Value("${jwt.mapping.authorities.claim-name}")
    private String JWT_AUTHORITIES_CLAIM_NAME;
    @Value("${jwt.mapping.user.claim-name}")
    private String USER_CLAIM_NAME ;


    @Override
    public ResponseEntity<AuthResponseDTO> auth(Authentication authentication) {

        String subject = authentication.getName();
        String scope = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        Account account = (Account)authentication.getPrincipal();
        Map<String,Object> claims = Map.of(
                JWT_AUTHORITIES_CLAIM_NAME,scope,
                USER_CLAIM_NAME,account.getAccountId().toString()
        );
        String jwtAccessToken = this.jwtUtils.generateAccessToken(subject,claims);

        AuthResponseDTO authResponseDTO = new AuthResponseDTO(true,jwtAccessToken,null);
        return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AuthResponseDTO> auth(AuthRequestDTO.UsernamePassword authRequestDTO) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(authRequestDTO.username(),authRequestDTO.password());
        Authentication authentication = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        String subject = authentication.getName();
        String scope = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        System.out.println(authentication);
        Account account = (Account)authentication.getPrincipal();
        Map<String,Object> claims = Map.of(
                JWT_AUTHORITIES_CLAIM_NAME,scope,
                USER_CLAIM_NAME,account.getAccountId().toString()
                );

        String jwtAccessToken = this.jwtUtils.generateAccessToken(subject,claims);
        String jwtRefreshToken = authRequestDTO.withRefreshToken() ? this.jwtUtils.generateRefreshToken(subject) : null;

        AuthResponseDTO authResponseDTO = new AuthResponseDTO(true,jwtAccessToken,jwtRefreshToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AuthResponseDTO> auth(AuthRequestDTO.RefreshToken authRequestDTO) {

        String subject = this.jwtUtils.extractSubject(authRequestDTO.refreshToken());
        if(subject == null) throw new RuntimeException("BAD REQUEST - TOKEN-SUBJECT - IS NULL");
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(subject);

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        String scope = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        System.out.println(userDetails);
        Account account = (Account)userDetails;
        Map<String,Object> claims = Map.of(
                JWT_AUTHORITIES_CLAIM_NAME,scope,
                USER_CLAIM_NAME,account.getAccountId().toString()
        );
        String jwtAccessToken = this.jwtUtils.generateAccessToken(subject,claims);
        String jwtRefreshToken = this.jwtUtils.generateRefreshToken(subject);

        AuthResponseDTO authResponseDTO = new AuthResponseDTO(true,jwtAccessToken,jwtRefreshToken);

        return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);
    }
}
