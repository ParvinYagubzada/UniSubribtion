package az.code.unisubribtion.models;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    Long id;
    Long value;
    @Enumerated(EnumType.STRING)
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    DateUnit unit;
}

