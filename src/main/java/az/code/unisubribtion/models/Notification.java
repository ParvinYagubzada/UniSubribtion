package az.code.unisubribtion.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notifications", schema = "public")
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.EAGER)
    @Column(name = "subscription_id")
    private Subscription subscription;
    @Column(name = "user_id")
    private Long userId;
    private LocalDateTime time;
}
