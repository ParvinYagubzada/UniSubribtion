package az.code.unisubribtion.services;

import az.code.unisubribtion.dtos.UserDTO;
import az.code.unisubribtion.models.Subscription;
import az.code.unisubribtion.models.SubscriptionUser;
import az.code.unisubribtion.repositories.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository repo;

    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }


    @Override
    public UserDTO insertUser(PasswordEncoder passwordEncoder, SubscriptionUser user) {

        UserDTO dtoUser = UserDTO.builder().build();

        return ;
    }
}
