package at.technikum.planner.functionality;

import at.technikum.planner.config.ApplicationConfigProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ApplicationConfigPropertiesTest {

    private ApplicationConfigProperties configProperties;

    @BeforeEach
    void setUp() {
        configProperties = new ApplicationConfigProperties();
    }

    @Test
    void testConfigurationPropertiesPrefix() {
        ConfigurationProperties configurationPropertiesAnnotation =
                ApplicationConfigProperties.class.getAnnotation(ConfigurationProperties.class);
        assertNotNull(configurationPropertiesAnnotation);
        assertEquals("app", configurationPropertiesAnnotation.prefix());
    }

    @Test
    void testComponentScanBasePackages() {
        ComponentScan componentScanAnnotation =
                ApplicationConfigProperties.class.getAnnotation(ComponentScan.class);
        assertNotNull(componentScanAnnotation);
        assertEquals(2, componentScanAnnotation.basePackages().length);
        assertEquals("at.technikum.planner", componentScanAnnotation.basePackages()[0]);
        assertEquals("at.technikum.dal", componentScanAnnotation.basePackages()[1]);
    }

    @Test
    void testEntityScan() {
        EntityScan entityScanAnnotation =
                ApplicationConfigProperties.class.getAnnotation(EntityScan.class);
        assertNotNull(entityScanAnnotation);
        assertEquals(1, entityScanAnnotation.value().length);
        assertEquals("at.technikum.dal.dao", entityScanAnnotation.value()[0]);
    }

    @Test
    void testEnableJpaRepositories() {
        EnableJpaRepositories enableJpaRepositoriesAnnotation =
                ApplicationConfigProperties.class.getAnnotation(EnableJpaRepositories.class);
        assertNotNull(enableJpaRepositoriesAnnotation);
        assertEquals(1, enableJpaRepositoriesAnnotation.basePackages().length);
        assertEquals("at.technikum.dal.repository", enableJpaRepositoriesAnnotation.basePackages()[0]);
    }
}