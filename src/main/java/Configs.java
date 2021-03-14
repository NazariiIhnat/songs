import gui.MainFrame;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("gui")
public class Configs {

    @Bean
    public MainFrame getMainFrame() {
        return new MainFrame();
    }
}
