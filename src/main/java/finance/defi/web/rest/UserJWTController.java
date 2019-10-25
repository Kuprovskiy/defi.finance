package finance.defi.web.rest;

import finance.defi.domain.User;
import finance.defi.security.jwt.JWTFilter;
import finance.defi.security.jwt.TokenProvider;
import finance.defi.service.TrustedDeviceService;
import finance.defi.service.UserService;
import finance.defi.web.rest.errors.EntityNotFoundException;
import finance.defi.web.rest.vm.LoginVM;

import com.fasterxml.jackson.annotation.JsonProperty;

import finance.defi.web.rest.vm.PhoneVM;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class UserJWTController {

    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final UserService userService;

    private final TrustedDeviceService trustedDeviceService;

    public UserJWTController(TokenProvider tokenProvider,
                             AuthenticationManagerBuilder authenticationManagerBuilder,
                             UserService userService,
                             TrustedDeviceService trustedDeviceService) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userService = userService;
        this.trustedDeviceService = trustedDeviceService;
    }

    @PostMapping("/mobile/authenticate")
    public ResponseEntity<JWTToken> authorizeMobile(HttpServletRequest request, @Valid @RequestBody PhoneVM phoneVM) {
        LoginVM loginVM = new LoginVM();
        loginVM.setUsername(phoneVM.getPhone());
        loginVM.setPassword(phoneVM.getPassword());
        loginVM.setRememberMe(phoneVM.isRememberMe());
        return authorize(request, loginVM);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authorize(HttpServletRequest request, @Valid @RequestBody LoginVM loginVM) {

        User currentUser = userService.getUserWithAuthoritiesByLogin(loginVM.getUsername()).orElseThrow(
            () -> new EntityNotFoundException("User not found"));

        // validate trust device
        validateTrustDevice(request, currentUser);
        // validate ip address

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
        String jwt = tokenProvider.createToken(authentication, rememberMe);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }

    private void validateTrustDevice(HttpServletRequest request, User currentUser) {
        this.trustedDeviceService.validateTrustDevice(request, currentUser);
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
