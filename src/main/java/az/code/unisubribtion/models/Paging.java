package az.code.unisubribtion.models;

import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Paging {
    private Long pageCount;
    private Long pageSize;
    private List<Subscription> subscriptions;
}
