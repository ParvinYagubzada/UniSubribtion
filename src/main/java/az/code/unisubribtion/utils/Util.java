package az.code.unisubribtion.utils;

import az.code.unisubribtion.models.DateUnit;
import az.code.unisubribtion.models.Duration;
import az.code.unisubribtion.models.Group;
import az.code.unisubribtion.models.Subscription;
import az.code.unisubribtion.repositories.SubscriptionRepository;
import com.github.javafaker.Faker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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

    public static void createSubs(int count, SubscriptionRepository repo) {
        Faker faker = new Faker();
        for (int i = 1; i <= 10; i++) {
            Group group = new Group();
            group.setName(faker.name().username());
            group.setUserId((long) faker.number().numberBetween(2, 101));
//            repo.save(group);
            for (int j = 0; j < 10; j++) {
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
                repo.save(subscription);
            }
            //groupRepo.save(group);
        }
    }
}
