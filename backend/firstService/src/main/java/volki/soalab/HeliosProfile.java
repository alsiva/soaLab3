package volki.soalab;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@Profile("helios")
public class HeliosProfile {

    @Bean
    public DataSource dataSourceHelios() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/studs");
        dataSource.setUsername("s312199");
        dataSource.setPassword("98K3jVleYX0U4kzo");
        return dataSource;
    }
}
