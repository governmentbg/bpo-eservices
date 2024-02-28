package bg.duosoft.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(
        basePackages = "bg.duosoft.data.repository",
        entityManagerFactoryRef = "bpoOnlineEntityManager",
        transactionManagerRef = "bpoOnlineTransactionManager"
)
@PropertySource("classpath:bpo-online/bpo-online.properties")
@EnableJpaAuditing
public class BpoOnlineDatabaseConfig {

  @Autowired
  private Environment env;

  @Bean
  @Primary
  public LocalContainerEntityManagerFactoryBean bpoOnlineEntityManager() {
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(bpoOnlineDataSource());
    em.setPackagesToScan("bg.duosoft.data.entity");

    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    em.setJpaVendorAdapter(vendorAdapter);

    HashMap<String, Object> properties = new HashMap<>();
    properties.put("hibernate.dialect", env.getProperty("bpo.online.persistence.jpa.database-platform"));
    properties.put("hibernate.show_sql", env.getProperty("bpo.online.persistence.hibernate.show_sql"));
    properties.put("javax.persistence.validation.mode","none");
    properties.put("hibernate.dynamic-update",true);
   // properties.put("hibernate.format_sql", true);
    em.setJpaPropertyMap(properties);
    return em;
  }

  @Bean
  @Primary
  public DataSource bpoOnlineDataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(env.getProperty("bpo.online.persistence.datasource.driver-class-name"));
    dataSource.setUrl(env.getProperty("bpo.online.persistence.datasource.url"));
    dataSource.setUsername(env.getProperty("bpo.online.persistence.datasource.username"));
    dataSource.setPassword(env.getProperty("bpo.online.persistence.datasource.password"));

    return dataSource;
  }

  @Bean
  @Primary
  public PlatformTransactionManager bpoOnlineTransactionManager() {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(bpoOnlineEntityManager().getObject());
    transactionManager.setEntityManagerFactory(bpoOnlineEntityManager().getObject());
    return transactionManager;
  }
}