package az.code.unisubribtion.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subscriptions", schema = "public")
@Entity
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "price_id")
    private Price price;
    @Column(name = "user_id")
    private Long userId;
    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    private Group group;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;
    @Column(name = "subscription_id")
    private LocalDateTime subscriptionTime;
    private Boolean hasNotification;
    private Boolean active;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "duration_id")
    private Duration duration;
}
