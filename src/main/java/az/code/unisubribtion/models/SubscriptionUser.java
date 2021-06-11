package az.code.unisubribtion.models;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", schema = "public")
@Entity
public class SubscriptionUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String name;
    private String surname;
    private String email;
    private Boolean active;
}

