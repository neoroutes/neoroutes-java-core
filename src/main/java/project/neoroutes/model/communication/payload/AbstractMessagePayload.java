package project.neoroutes.model.communication.payload;

import lombok.AllArgsConstructor;
import project.neoroutes.model.communication.Payload;

@AllArgsConstructor
public abstract class AbstractMessagePayload implements Payload {
    private final PayloadType payloadType;

    public final PayloadType getPayloadType(){
        return payloadType;
    }

    public enum PayloadType {
        DIRECT_MESSAGE, DH_HANDSHAKE
    }
}
