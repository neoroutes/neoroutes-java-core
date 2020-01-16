package project.neoroutes.aes;

import javax.crypto.spec.IvParameterSpec;

public interface IVRepository {
    default IvParameterSpec getIvParameterSpec(){
        byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        return new IvParameterSpec(iv);
    }
}
