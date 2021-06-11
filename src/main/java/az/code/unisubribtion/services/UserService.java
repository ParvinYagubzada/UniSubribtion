package az.code.unisubribtion.services;

import az.code.unisubribtion.dtos.UserDTO;
import az.code.unisubribtion.models.SubscriptionUser;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface UserService {
    UserDTO insertUser(PasswordEncoder passwordEncoder, SubscriptionUser user);
}
