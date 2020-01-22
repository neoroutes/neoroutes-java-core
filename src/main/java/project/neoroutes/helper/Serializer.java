package project.neoroutes.helper;

import java.io.*;

public class Serializer<E extends Serializable> {

    public byte[] serialize(E e) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(e);
            out.flush();
            return bos.toByteArray();
        } finally {
            try {
                bos.close();
            } catch (IOException ignored) {}
        }
    }


    public E deserialize(byte[] ba) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(ba);
        ObjectInput in = null;
        try {
            in = new ObjectInputStream(bis);
            return (E) in.readObject();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ignored) {}
        }
    }

}
