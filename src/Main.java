import br.com.fiap.gs.model.Agroclimatic;
import br.com.fiap.gs.model.ClimateAlert;
import br.com.fiap.gs.model.Farmer;
import br.com.fiap.gs.model.Property;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    static Farmer user1 = new Farmer("João", "joao@gmil.com", "1234");
    static Property property1User1 = new Property(1, "Recanto da Luz", ".", ".");
    static Agroclimatic agroclimatic1property1 = new Agroclimatic(0, LocalDateTime.now(), 22, 7, 0, 64, 5);
    static ClimateAlert climateAlert1property1 = new ClimateAlert(0, "Risco de fortes chuvas!", "CRITICO", "CHUVA_INTENSA", LocalDate.now());

    public static void main(String[] args) {
        System.out.println("TESTANDO AS CLASSES: \n");
        System.out.println(user1);
        System.out.println(property1User1);
        System.out.println(agroclimatic1property1);
        System.out.println(climateAlert1property1);
    }
}