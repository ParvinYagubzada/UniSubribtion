package az.code.unisubribtion.dtos;

import az.code.unisubribtion.models.Price;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private Long id;
    private String name;
    private List<Price> totalPrices;
    private Integer subscriptionCount;
    private LocalDateTime shortestDeadline;
}
