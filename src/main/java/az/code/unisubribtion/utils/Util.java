package az.code.unisubribtion.utils;

import az.code.unisubribtion.models.*;
import az.code.unisubribtion.repositories.GroupRepository;
import az.code.unisubribtion.repositories.SubscriptionRepository;
import az.code.unisubribtion.repositories.UserRepository;
import com.github.javafaker.Faker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;

public class Util {
    public static Pageable preparePage(Integer pageNo, Integer pageSize, String sortBy) {
        return PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
    }

    public static <T> List<T> getResult(Page<T> pageResult) {
        if (pageResult.hasContent()) {
            return pageResult.getContent();
        } else {
            return new LinkedList<>();
        }
    }

    public static ChronoUnit convertUnit(DateUnit unit) {
        switch (unit) {
            case DAY:
                return ChronoUnit.DAYS;
            case WEEK:
                return ChronoUnit.WEEKS;
            case MONTH:
                return ChronoUnit.MONTHS;
        }
        return ChronoUnit.YEARS;
    }

    public static void createUsers(int count, UserRepository repo) {
        Faker faker = new Faker();
        for (int i = 1; i <= count; i++) {
            SubscriptionUser user = new SubscriptionUser();
            user.setName(faker.name().firstName());
            user.setUsername(faker.name().username());
            user.setSurname(faker.name().lastName());
            user.setEmail(faker.internet().emailAddress());
            user.setPassword(faker.phoneNumber().cellPhone());
            user.setActive(true);
            repo.save(user);
        }
    }

    public static void createSubs(int count, GroupRepository groupRepo, SubscriptionRepository repo) {
        Faker faker = new Faker();
        for (int i = 1; i <= count; i++) {
            Group group = new Group();
            group.setName(faker.name().username());
            group.setUserId((long) faker.number().numberBetween(2, 101));
            groupRepo.save(group);
            for (int j = 0; j < count; j++) {
                Duration duration = Duration.builder()
                        .unit(DateUnit.values()[faker.number().numberBetween(0, 4)])
                        .value((long) faker.number().numberBetween(1, 15))
                        .build();
                Subscription subscription = Subscription.builder()
                        .lastPaymentDay(LocalDateTime.now())
                        .userId(i + 100L)
                        .name(faker.animal().name())
                        .duration(duration)
                        .group(group)
                        .price(faker.number().randomDouble(3, 5, 100))
                        .hasNotification(false)
                        .active(true)
                        .build();
                subscription = subscription.toBuilder().nextPaymentDay(subscription.getLastPaymentDay().plus(subscription
                                .getDuration()
                                .getValue(),
                        convertUnit(subscription.getDuration().getUnit()))).build();
                repo.save(subscription);
            }
        }
    }

//    public static String encode(String password) throws NoSuchAlgorithmException {
//        MessageDigest md = MessageDigest.getInstance("MD5");
//        md.update(password.getBytes());
//        byte[] digest = md.digest();
//        return DatatypeConverter.printHexBinary(digest).toUpperCase();
//    }
}
