package az.code.unisubribtion.dtos;

import az.code.unisubribtion.models.Group;
import az.code.unisubribtion.models.Price;
import az.code.unisubribtion.models.Subscription;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class GroupDTO {
    private Long id;
    private String name;
    private List<Price> totalPrices;
    private Integer subscriptionCount;
    private LocalDateTime shortestDeadline;

    public GroupDTO(Group group, List<Subscription> subs) {
        this.id = group.getId();
        this.name = group.getName();
    }
}
