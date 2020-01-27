package project.neoroutes.model.communication.payload;

import lombok.*;
import project.neoroutes.model.SignedData;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class SimpleTextDirectMessagePayload extends AbstractMessagePayload implements Serializable {
    private SignedData<TextMessage> textMessage;
    public SimpleTextDirectMessagePayload() {
        super(PayloadType.DIRECT_MESSAGE);
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class TextMessage {
        private String message;
        private long time;
    }
}
