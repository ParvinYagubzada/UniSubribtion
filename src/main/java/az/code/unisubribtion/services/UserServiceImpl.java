package az.code.unisubribtion.services;

import az.code.unisubribtion.dtos.UserDTO;
import az.code.unisubribtion.exceptions.EmailAlreadyExists;
import az.code.unisubribtion.exceptions.UsernameAlreadyExists;
import az.code.unisubribtion.models.SubscriptionUser;
import az.code.unisubribtion.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @Override
    public UserDTO createUser(SubscriptionUser user) {
        if (repo.findUserByEmail(user.getEmail()) != null)
            throw new EmailAlreadyExists();
        if (repo.findUserByUsername(user.getUsername()) != null)
            throw new UsernameAlreadyExists();
        return new UserDTO(repo.save(user.toBuilder()
                .password(encoder.encode(user.getPassword()))
                .build()
        ));
    }
}
