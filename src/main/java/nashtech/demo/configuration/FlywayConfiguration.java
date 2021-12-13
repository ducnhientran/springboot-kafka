package nashtech.demo.configuration;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlywayConfiguration {

    @Bean
    public Flyway flyway(@Qualifier("dataSource") DataSource dataSource){
        Flyway flyway =  Flyway.configure()
                .locations("classpath:db/migration")
                .dataSource(dataSource)
                .baselineVersion("0")
                .baselineOnMigrate(false)
                .outOfOrder(false)
                .load();
        flyway.repair();
        flyway.migrate();
        return flyway;
    }
}
