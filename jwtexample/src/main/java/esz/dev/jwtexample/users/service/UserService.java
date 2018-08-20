package esz.dev.jwtexample.users.service;

import esz.dev.jwtexample.users.dao.Authority;
import esz.dev.jwtexample.users.dao.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private PasswordEncoder passwordEncoder =  new BCryptPasswordEncoder();

    // !!! This should be taken from a database, for a demo we use hardcoded values.
    private List<Authority> adminAuth = Arrays.asList(Authority.ROLE_ADMIN);
    private List<Authority> userAuth = Arrays.asList(Authority.ROLE_USER);
    private List<User> users = Arrays.asList(
            new User(1, "admin", passwordEncoder.encode("admin"), adminAuth, true, new Date()),
            new User(1, "user", passwordEncoder.encode("user"), userAuth, true, new Date()));

    public Optional<User> getUserByUsername(String username) {
        return users.stream().filter(user -> user.getUsername().equals(username)).findFirst();
    }
}
