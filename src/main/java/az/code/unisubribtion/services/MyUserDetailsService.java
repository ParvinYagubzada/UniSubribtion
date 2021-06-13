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

import java.util.List;

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    public MyUserDetailsService(UserRepository dao) {
        this.repository = dao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SubscriptionUser user = repository.findUserByUsername(username);
        List<GrantedAuthority> authorities = buildUserAuthority();
        return buildUserForAuthentication(user, authorities);
    }

    private List<GrantedAuthority> buildUserAuthority() {
        return List.of(new SimpleGrantedAuthority("USER"));
    }

    private User buildUserForAuthentication(SubscriptionUser user, List<GrantedAuthority> authorities) {
        return new User(user.getUsername(), user.getPassword(),
                user.getActive(), true, true, true, authorities);
    }
}
