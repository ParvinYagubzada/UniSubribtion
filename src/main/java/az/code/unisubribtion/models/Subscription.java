package az.code.unisubribtion.models;

import lombok.*;

import javax.persistence.*;

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
    @JoinColumn(name = "subscription_id", referencedColumnName = "id")
    private Price price;
    @Column(name = "user_id")
    private Long userId;
    @ManyToOne
    @JoinColumn(name = "subscription_id", referencedColumnName = "id")
    private Group group;
    @ManyToOne
    @JoinColumn(name = "subscription_id", referencedColumnName = "id")
    private Category category;
}
