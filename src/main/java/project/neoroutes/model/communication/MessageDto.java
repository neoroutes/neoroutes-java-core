package project.neoroutes.model.communication;

import lombok.*;
import project.neoroutes.model.SignedData;
import project.neoroutes.model.UserInfo;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MessageDto<E extends Payload> implements Serializable {
    private SignedData<Message<E>> message;
    List<UserInfo> route;
}
