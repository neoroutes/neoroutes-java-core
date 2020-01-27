package project.neoroutes.model.communication;

import lombok.*;
import project.neoroutes.model.UserInfo;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Message<E extends Payload> {
    private String id;
    private UserInfo userInfo;
    private E payload;
}
