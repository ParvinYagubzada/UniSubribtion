package az.code.unisubribtion.services;

import az.code.unisubribtion.dtos.UserDTO;
import az.code.unisubribtion.exceptions.EmailAlreadyExists;
import az.code.unisubribtion.exceptions.UsernameAlreadyExists;
import az.code.unisubribtion.models.SubscriptionUser;
import az.code.unisubribtion.repositories.UserRepository;
import com.github.javafaker.Faker;
import org.apache.tomcat.jni.User;
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

        Faker faker = new Faker();
        for (int i = 1; i <= 100; i++) {
            SubscriptionUser user = new SubscriptionUser();
            user.setName(faker.name().firstName());
            user.setUsername(faker.name().username());
            user.setSurname(faker.name().lastName());
            user.setEmail(faker.internet().emailAddress());
            user.setPassword(faker.phoneNumber().cellPhone());
            user.setActive(true);
        }
    }

    @Override
    public UserDTO createUser(SubscriptionUser user) {
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
    public UserDTO authUser(String username, String pass) {
        SubscriptionUser user = repo.findUserByUsername(username);
        if (username.equals(user.getUsername()) && pass.equals(user.getPassword())) {
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
        return DatatypeConverter.printHexBinary(digest).toUpperCase();
    }
}
