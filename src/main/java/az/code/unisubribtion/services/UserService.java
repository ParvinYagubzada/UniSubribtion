package az.code.unisubribtion.services;

import az.code.unisubribtion.dtos.UserDTO;
import az.code.unisubribtion.models.SubscriptionUser;

import java.security.NoSuchAlgorithmException;

public interface UserService {

    UserDTO createUser(SubscriptionUser user) throws NoSuchAlgorithmException;

    UserDTO authUser(String username, String pass) throws NoSuchAlgorithmException;
}
