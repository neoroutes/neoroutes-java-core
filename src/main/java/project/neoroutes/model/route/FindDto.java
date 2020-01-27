package project.neoroutes.model.route;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.neoroutes.model.SignedData;
import project.neoroutes.model.UserInfo;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class FindDto implements Serializable {
    SignedData<FindRequest> request;
    List<UserInfo> passedRoute;
}
