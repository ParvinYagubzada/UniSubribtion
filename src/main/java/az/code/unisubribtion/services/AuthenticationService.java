package az.code.unisubribtion.services;


import az.code.unisubribtion.models.SubscriptionUser;
import az.code.unisubribtion.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("userDetailsService")
public class AuthenticationService implements UserDetailsService {

    private final UserRepository repository;

    public AuthenticationService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SubscriptionUser user = repository.findUserByUsername(username);
        return buildUserForAuthentication(user);
    }

    private User buildUserForAuthentication(SubscriptionUser user) {
        return new User(user.getUsername(), user.getPassword(),
                user.getActive(), true, true, true, List.of(new SimpleGrantedAuthority("USER")));
    }
}
