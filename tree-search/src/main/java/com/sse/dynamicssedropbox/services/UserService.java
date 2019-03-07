package com.sse.dynamicssedropbox.services;

import com.sse.dynamicssedropbox.models.User;
import com.sse.dynamicssedropbox.models.UserPrincipal;
import com.sse.dynamicssedropbox.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public class UserService implements UserDetailsService {

    private UserRepository repository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repository, BCryptPasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User save(User user) {
        return repository.save(user);
    }

    public User register(String username, String password) {
        if(repository.findByUsername(username) == null) {
            return repository.save(new User()
                    .setUsername(username)
                    .setPassword(passwordEncoder.encode(password)));
        }
        return null;
    }

    public User login(String username, String password) {
        User user = repository.findByUsername(username);
        if(user == null || !passwordEncoder.matches(password, user.getPassword())) return null;
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);
        if(user == null) throw new UsernameNotFoundException(username);
        return new UserPrincipal(user);
    }

    public User findByUsername(String username) {
        return repository.findByUsername(username);
    }
}
