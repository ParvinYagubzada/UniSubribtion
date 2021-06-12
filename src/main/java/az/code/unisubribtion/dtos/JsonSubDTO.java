package az.code.unisubribtion.dtos;


import az.code.unisubribtion.models.Duration;
import az.code.unisubribtion.models.Group;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class JsonSubDTO {
    private Long id;
    private String name;
    private Double price;
    private Long userId;
    private Group group;
    private String lastPaymentDay;
    private Boolean hasNotification;
    private Boolean active;
    private Duration duration;
}
