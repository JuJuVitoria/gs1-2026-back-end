import br.com.fiap.gs.model.Farmer;
import br.com.fiap.gs.model.Property;

public class Main {
    static Farmer user1 = new Farmer("João", "joao@gmil.com", "1234");
    static Property property1User1 = new Property(1, "Recanto da Luz", ".", ".");

    public static void main(String[] args) {
        System.out.println(user1.getName());
        System.out.println(property1User1.getFarmName());
    }
}
