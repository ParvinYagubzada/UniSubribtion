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
    private String name;
    private String context;
    private Boolean hasSeen;
    @Column(name = "subscription_id")
    private Long subscriptionId;
    @Column(name = "user_id")
    private Long userId;
    private LocalDateTime time;
}
