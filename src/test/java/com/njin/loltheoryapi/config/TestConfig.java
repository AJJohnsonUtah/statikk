/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheoryapi.config;

import com.njin.loltheory.dao.ChampSpecWinRateDao;
import com.njin.loltheory.riotapi.service.RiotApiKeyLimitService;
import com.njin.loltheory.riotapi.service.RiotApiService;
import com.njin.loltheory.service.ChampSpecWinRateService;
import com.njin.loltheory.stats.service.ChampionWinRateService;
import com.njin.loltheoryapi.controller.ChampionWinRateController;
import com.njin.loltheoryapi.controller.StaticDataController;
import com.njin.loltheoryapi.controller.SummonerDataController;
import java.io.IOException;
import java.util.Properties;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author AJ
 */

@Configuration
@EnableTransactionManagement
@Profile("test")
@PropertySource(value={"classpath:application.properties"})
@WebAppConfiguration 
public class TestConfig {
 
    @Autowired
    private Environment environment;


    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        return messageSource;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
        dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
        dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
        dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityMangerFactory() throws NamingException {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan(new String[]{"com.njin"});
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        factoryBean.setJpaProperties(jpaProperties());
        return factoryBean;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        return hibernateJpaVendorAdapter;
    }

    private Properties jpaProperties() {
        Properties p = new Properties();
        p.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        p.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        p.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
        p.put("org.hibernate.persister.entity", true);
        return p;
    }

    @Bean
    @Autowired
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(emf);
        return txManager;
    }   

    @Bean
    public RiotApiService getRiotApiService() throws IOException {
        return new RiotApiService();
    }
    
    @Bean
    public RiotApiKeyLimitService getRiotApiKeyService() throws IOException {
        return new RiotApiKeyLimitService();
    }
    
    @Bean
    public StaticDataController getStaticDataController() {
        return new StaticDataController();
    }
    
    @Bean
    public SummonerDataController getSummonerDataController() {
        return new SummonerDataController();
    }
    
    @Bean
    public ChampSpecWinRateService getChampSpecWinRateService() {
        return new ChampSpecWinRateService();
    }
    
    @Bean
    public ChampionWinRateService getChampionWinRateService() {
        return new ChampionWinRateService();
    }
    
    @Bean
    public ChampSpecWinRateDao getChampSpecWinRateDao() {
        return new ChampSpecWinRateDao();
    }
    
    @Bean
    public ChampionWinRateController getChampionWinRateController() {
        return new ChampionWinRateController();
    }
}
