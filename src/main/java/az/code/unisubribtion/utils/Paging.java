package az.code.unisubribtion.utils;

import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Paging<T> {
    private Long pageCount;
    private Long pageSize;
    private List<T> subscriptions;
}
