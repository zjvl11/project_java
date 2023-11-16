package com.javateam.memberProject.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class MyBatisConfig {

	@Bean(name="dataSource")
    @Primary
    @ConfigurationProperties(prefix="spring.datasource.hikari") 
    public DataSource hikariDataSource() {
        return DataSourceBuilder.create()
        			.type(HikariDataSource.class)
        			.build();
    }
	
	// @Bean(name="myBatisHikariCP")
	@Bean(name="hikariCP")
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		
	    SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
	    factoryBean.setDataSource(hikariDataSource());
	    
	    factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
				   .getResources("classpath:/mapper/*.xml"));
	    
	    return factoryBean.getObject();
	}
	
	@Bean(name="sqlSession")	
	public SqlSessionTemplate sqlSessionTemplate() throws Exception {
		
		return new SqlSessionTemplate(sqlSessionFactory());
	}
	
	// 10.19
	@Bean
	@Qualifier(value = "transactionManager")
	// 주의) MyBatis & JPA 동시에 트랜잭션 매니저를 사용하고 있는 상황에서
	// JPA 트랜잭션 매니저와의 충돌 문제로 @Qualifier 적용 
	// 트랜잭션 매니저 빈(bean) 이름 구체 지정 : transactionManager 
    public PlatformTransactionManager getTransactionManager() {
	        
		return new DataSourceTransactionManager(this.hikariDataSource());
	}

}