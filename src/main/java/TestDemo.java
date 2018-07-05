import java.util.ArrayList;
import java.util.List;

public class TestDemo {

    static class OOMObject{

    }
    public static void main(String[] args) {
        List list = new ArrayList();
        while(true){
            list.add(new OOMObject());
        }

    }

}
