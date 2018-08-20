package esz.dev.jwtexample.security.controller;

import esz.dev.jwtexample.security.JwtTokenUtil;
import esz.dev.jwtexample.security.dto.JwtAuthenticationRequest;
import esz.dev.jwtexample.security.dto.JwtAuthenticationResponse;
import esz.dev.jwtexample.security.dto.JwtError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private String tokenHeader;
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager,
                                        @Qualifier("jwtUserDetailsService") UserDetailsService userDetailsService,
                                        @Value("${jwt.header}") String tokenHeader, JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.tokenHeader = tokenHeader;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping(value = "/api/login", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public JwtAuthenticationResponse createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest)
            throws DisabledException, BadCredentialsException {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        // Reload password post-security so we can generate the token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        return jwtTokenUtil.generateToken(userDetails);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({BadCredentialsException.class})
    public JwtError handleAuthenticationException() {
        return new JwtError("Bad credentials!");
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({DisabledException.class})
    public JwtError handleDisabledException() {
        return new JwtError("User is disabled");
    }

    private void authenticate(String username, String password) throws DisabledException, BadCredentialsException {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
