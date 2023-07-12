package at.technikum.planner.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.ResourceBundle;

@ConfigurationProperties(prefix = "app")
@ComponentScan(basePackages = {"at.technikum.planner", "at.technikum.dal"})
@EntityScan("at.technikum.dal.dao")
@EnableJpaRepositories(basePackages = {"at.technikum.dal.repository"})
public class ApplicationConfigProperties {
    private ResourceBundle bundle;

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public ResourceBundle getBundle() {
        return bundle;
    }
}
