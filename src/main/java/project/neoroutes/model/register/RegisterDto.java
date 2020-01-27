package project.neoroutes.model.register;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.neoroutes.model.SignedData;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RegisterDto implements Serializable {
    private SignedData<RegisterInfo> registerInfo;
}
