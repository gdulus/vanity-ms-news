package ms.vanity.news

import org.apache.log4j.PropertyConfigurator
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.core.io.ClassPathResource

@ComponentScan
@EnableAutoConfiguration
class Application {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Application.class, args);
        // overwrite Log4J configuration
        ConfigSlurper configSlurper = new ConfigSlurper()
        ConfigObject config = configSlurper.parse(new ClassPathResource("log4j.groovy").URL)
        PropertyConfigurator.configure(config.toProperties())
    }
}
