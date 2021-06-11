package az.code.unisubribtion.services;

import az.code.unisubribtion.dtos.UserDTO;
import az.code.unisubribtion.models.SubscriptionUser;

public interface UserService {
    UserDTO createUser(SubscriptionUser user);
}
