package project.neoroutes.model.route;

import lombok.*;
import project.neoroutes.model.SignedData;
import project.neoroutes.model.UserInfo;
import project.neoroutes.model.register.RegisterInfo;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class FoundDto implements Serializable {
    SignedData<FindRequest> request;
    SignedData<RegisterInfo> result;
    List<UserInfo> route;
}
