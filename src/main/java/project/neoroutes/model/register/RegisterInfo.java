package project.neoroutes.model.register;

import lombok.*;
import project.neoroutes.model.UserInfo;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class RegisterInfo extends UserInfo {
    private long time;
}
