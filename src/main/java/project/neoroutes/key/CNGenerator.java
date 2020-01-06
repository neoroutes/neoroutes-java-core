package project.neoroutes.key;

public class CNGenerator {
    public static String getCN(String userId){
        return "cn="+ userId + ".neoroutes";
    }
}
