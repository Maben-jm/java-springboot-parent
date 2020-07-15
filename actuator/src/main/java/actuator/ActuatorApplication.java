package actuator;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description:
 * @author: maben
 * @createDate: 2020/4/16
 */
@SpringBootApplication
@EnableAdminServer
public class ActuatorApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ActuatorApplication.class);
    }

}
