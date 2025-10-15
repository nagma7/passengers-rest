package kz.railways.passp.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class DatabaseConfig {
	
		@Primary
		@Bean(name = "db2")
		@ConfigurationProperties(prefix = "spring.datasource.db2")
		public DataSource dataSource1() {
			return DataSourceBuilder.create().build();
		}
			
		@Primary 
		@Bean(name = "db2template")
		public NamedParameterJdbcTemplate jdbcTemplate1(@Qualifier("db2") DataSource ds) {
			return new NamedParameterJdbcTemplate(ds);
		}
		
		 
		@Bean(name = "postgres")
		@ConfigurationProperties(prefix = "spring.datasource.postgres")
		public DataSource dataSource2() {
			return  DataSourceBuilder.create().build();
		}	
		
		@Bean(name = "postgrestemplate")
		public NamedParameterJdbcTemplate jdbcTemplate2(@Qualifier("postgres") DataSource ds) {
			return new NamedParameterJdbcTemplate(ds);
		}
}
