import br.com.fiap.gs.config.ApplicationContext;
import br.com.fiap.gs.view.LoginMenu;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ApplicationContext();
        context.seedData();

        System.out.println("""
        
                █████╗ ███████╗████████╗██████╗  ██████╗  ██████╗██████╗  ██████╗ ██████╗
               ██╔══██╗██╔════╝╚══██╔══╝██╔══██╗██╔═══██╗██╔════╝██╔══██╗██╔═══██╗██╔══██╗
               ███████║███████╗   ██║   ██████╔╝██║   ██║██║     ██████╔╝██║   ██║██████╔╝
               ██╔══██║╚════██║   ██║   ██╔══██╗██║   ██║██║     ██╔══██╗██║   ██║██╔═══╝
               ██║  ██║███████║   ██║   ██║  ██║╚██████╔╝╚██████╗██║  ██║╚██████╔╝██║
               ╚═╝  ╚═╝╚══════╝   ╚═╝   ╚═╝  ╚═╝ ╚═════╝  ╚═════╝╚═╝  ╚═╝ ╚═════╝ ╚═╝
        
        ═══════════════════════════════════════════════════════════════════════════════════════
                                        BEM-VINDO AO ASTROCROP
        ═══════════════════════════════════════════════════════════════════════════════════════
        
        Plataforma de inteligência agroclimática que transforma dados climáticos
        e informações inspiradas em tecnologias espaciais em recomendações práticas
        para pequenos e médios produtores rurais.
        """);

        context.showUsersTest();

        LoginMenu loginMenu = context.getLoginMenu();
        loginMenu.showLoginMenu();
    }
}