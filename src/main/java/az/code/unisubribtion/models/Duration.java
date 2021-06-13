package az.code.unisubribtion.models;

import az.code.unisubribtion.utils.DateUnit;
import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "durations", schema = "public")
@Entity
public class Duration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long value;
    @Enumerated(EnumType.STRING)
    private DateUnit unit;
}

