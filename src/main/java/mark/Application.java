package mark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration(exclude={BatchAutoConfiguration.class})
public class Application {

    public static void main(String[] args) throws Exception {
    	SpringApplication app = new SpringApplication(Application.class);
        app.setShowBanner(true);
        app.run(args);
    }
}
