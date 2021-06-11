package az.code.unisubribtion.services;

import az.code.unisubribtion.dtos.UserDTO;
import az.code.unisubribtion.exceptions.EmailAlreadyExists;
import az.code.unisubribtion.exceptions.UsernameAlreadyExists;
import az.code.unisubribtion.models.SubscriptionUser;
import az.code.unisubribtion.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repo;

    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDTO createUser(SubscriptionUser user) throws NoSuchAlgorithmException {
        if (repo.findUserByEmail(user.getEmail()) != null)
            throw new EmailAlreadyExists();
        if (repo.findUserByUsername(user.getUsername()) != null)
            throw new UsernameAlreadyExists();
        return new UserDTO(repo.save(user.toBuilder()
                .password((user.getPassword()))
                .build()
        ));
    }

    @Override
    public UserDTO authUser(String username, String pass) throws NoSuchAlgorithmException {
        SubscriptionUser user = repo.findUserByUsername(username);
//        System.out.println(user.getUsername());
//        System.out.println(username);
//        System.out.println(pass);
//        System.out.println(user.getPassword());
        System.out.println(encode(pass));
        System.out.println(encode(pass));
        if (username.equals(user.getUsername()) && pass.equals(user.getPassword())) {
            System.out.println("Auth");
            return new UserDTO(repo.save(user.toBuilder()
                    .password(user.getPassword())
                    .build()
            ));
        }
        return null;
    }

    public String encode(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String myHash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        return myHash;
    }
}
