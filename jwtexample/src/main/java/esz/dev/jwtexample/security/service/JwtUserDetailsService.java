package esz.dev.jwtexample.security.service;

import esz.dev.jwtexample.security.JwtUserFactory;
import esz.dev.jwtexample.users.dao.User;
import esz.dev.jwtexample.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    private UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No user found with name of " + username));
        return JwtUserFactory.create(user);
    }
}
