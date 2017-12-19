/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */

package com.mainbo.jy.boot;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;
import com.mainbo.jy.common.orm.DefaultSqlMapping;
import com.mainbo.jy.common.orm.SqlMapping;
import com.mainbo.jy.common.page.MysqlPageSqlHelper;
import com.mainbo.jy.common.page.PageSqlHelper;
import com.mainbo.jy.manage.meta.DefaultMetaProvider;
import com.mainbo.jy.utils.SpringContextHolder;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: RootConfig.java, v 1.0 2017年11月13日 下午5:46:24 tmser Exp $
 */
@Configuration
@EnableTransactionManagement
@Import(PropertiesConfig.class)
public class DbConfig {

  @Value("${jdbc.url}")
  private String url;

  @Value("${jdbc.username}")
  private String username;

  @Value("${jdbc.password}")
  private String password;

  @Value("${druid.initialSize:2}")
  private Integer initialSize;

  @Value("${druid.minIdle:2}")
  private Integer minIdle;

  @Value("${druid.maxActive:50}")
  private Integer maxActive;

  @Value("${druid.maxWait:60000}")
  private Long maxWait;

  @Value("${druid.timeBetweenEvictionRunsMillis:60000}")
  private Long timeBetweenEvictionRunsMillis;

  @Value("${druid.minEvictableIdleTimeMillis:30000}")
  private Long minEvictableIdleTimeMillis;

  @Value("${druid.validationQuery:select 'x'}")
  private String validationQuery;

  @Value("${druid.testWhileIdle:true}")
  private Boolean testWhileIdle;

  @Value("${druid.poolPreparedStatements:false}")
  private Boolean poolPreparedStatements;

  @Value("${druid.testOnReturn:false}")
  private Boolean testOnReturn;

  @Value("${druid.maxPoolPreparedStatementPerConnectionSize:20}")
  private Integer maxPoolPreparedStatementPerConnectionSize;

  @Value("${druid.filters:stat,slf4j,wall,mergeStat}")
  private String filters;

  @Value("${druid.sharePreparedStatements:false}")
  private Boolean sharePreparedStatements;

  @Value("${druid.testOnBorrow:false}")
  private Boolean testOnBorrow;

  @Value("${druid.dbType:}")
  private String dbType;

  @Bean(initMethod = "init", destroyMethod = "close")
  @Lazy(false)
  public DataSource dataSource() throws SQLException {
    DruidDataSource dataSource = new DruidDataSource();
    dataSource.setUrl(url);
    dataSource.setUsername(username);
    dataSource.setPassword(password);
    dataSource.setDbType(dbType);

    dataSource.setValidationQuery(validationQuery);
    dataSource.setInitialSize(initialSize);
    dataSource.setMaxActive(maxActive);
    dataSource.setMinIdle(minIdle);
    dataSource.setMaxWait(maxWait);
    dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
    dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);

    dataSource.setTestOnReturn(testOnReturn);
    dataSource.setTestOnBorrow(testOnBorrow);
    dataSource.setTestWhileIdle(testWhileIdle);

    dataSource.setPoolPreparedStatements(poolPreparedStatements);
    dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
    dataSource.setSharePreparedStatements(sharePreparedStatements);
    dataSource.setFilters(filters);

    return dataSource;
  }

  @Bean
  public JdbcTemplate jdbcTemplate() throws SQLException {
    return new JdbcTemplate(dataSource());
  }

  @Bean
  public PlatformTransactionManager transactionManager() throws SQLException {
    return new DataSourceTransactionManager(dataSource());
  }

  @Bean(initMethod = "migrate")
  @Lazy(false)
  public Flyway flywayDbInit(@Value("${jdbc.url}") String url, @Value("${jdbc.username}") String username,
      @Value("${jdbc.password}") String password) {
    Flyway flyway = new Flyway();
    flyway.setLocations("dbmigrate");
    MysqlDataSource dataSourse = new MysqlDataSource();
    dataSourse.setUser(username);
    dataSourse.setPassword(password);
    dataSourse.setUrl(url);
    flyway.setDataSource(dataSourse);
    flyway.setValidateOnMigrate(false);
    flyway.setBaselineOnMigrate(true);
    flyway.setPlaceholderPrefix("ignore");
    flyway.setBaselineVersionAsString("0");
    flyway.setOutOfOrder(false);
    return flyway;
  }

  @Bean
  public SqlMapping sqlMapping() {
    return new DefaultSqlMapping();
  }

  @Bean
  public PageSqlHelper pageSqlHelper() {
    return new MysqlPageSqlHelper();
  }

  @Bean
  public SpringContextHolder springContextHolder() {
    return new SpringContextHolder();
  }

  @Bean
  public DefaultMetaProvider.DefaultPhaseMetaProvider phaseMetaProvider() {
    return new DefaultMetaProvider.DefaultPhaseMetaProvider();
  }

  @Bean
  public DefaultMetaProvider.DefaultPhaseSubjectMetaProvider phaseSubjectMetaProvider() {
    return new DefaultMetaProvider.DefaultPhaseSubjectMetaProvider();
  }

  @Bean
  public DefaultMetaProvider.DefaultPhaseGradeMetaProvider phaseGradeMetaProvider() {
    return new DefaultMetaProvider.DefaultPhaseGradeMetaProvider();
  }

  @Bean
  public DefaultMetaProvider.DefaultOrgTypeMetaProvider orgTypeMetaProvider() {
    return new DefaultMetaProvider.DefaultOrgTypeMetaProvider();
  }
}
