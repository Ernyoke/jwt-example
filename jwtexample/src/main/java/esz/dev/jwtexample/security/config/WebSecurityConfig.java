package esz.dev.jwtexample.security.config;

import esz.dev.jwtexample.security.JwtAuthenticationEntryPoint;
import esz.dev.jwtexample.security.filters.JwtAuthenticationTokenFilter;
import esz.dev.jwtexample.security.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(1)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    private JwtUserDetailsService jwtUserDetailsService;
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    public WebSecurityConfig(JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter,
                             JwtUserDetailsService jwtUserDetailsService,
                             JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.jwtAuthenticationTokenFilter = jwtAuthenticationTokenFilter;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .headers().cacheControl().disable()
            .and()
            .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and()
            .authorizeRequests()
            .antMatchers("/api/login").permitAll()
            .antMatchers("/api/admin").hasAnyRole("ADMIN")
            .antMatchers("/api/user").hasRole("USER");

        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    // Used to encode our password.
    public PasswordEncoder passwordEncoderBean() {
        return new BCryptPasswordEncoder();
    }

    // This configuration allows to link your personal userservice with the Spring security userservice.
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoderBean());
    }

    /*
    * Attempts to authenticate the passed Authentication object, returning a fully populated Authentication object
    * (including granted authorities) if successful.
    */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
