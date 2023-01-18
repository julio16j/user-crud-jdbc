package com.usercrudjdbc.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
public class DataSourceConfiguration {
	
	@Bean
	public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
	    DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
	    dataSourceInitializer.setDataSource(dataSource);
	    ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
	    databasePopulator.addScript(new ClassPathResource("schema.sql"));
	    dataSourceInitializer.setDatabasePopulator(databasePopulator);
	    dataSourceInitializer.setEnabled(true);
	    return dataSourceInitializer;
	}
	
}
