package az.code.unisubribtion.dtos;

import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private Integer count;
    private String context;
}
