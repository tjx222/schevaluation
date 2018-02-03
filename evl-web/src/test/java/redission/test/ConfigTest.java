/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */

package redission.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tmser.schevaluation.uc.bo.Login;
import com.tmser.schevaluation.uc.bo.User;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: ConfigTest.java, v 1.0 2017年11月9日 下午4:46:09 tmser Exp $
 */
@Configuration
public class ConfigTest {

  @Bean
  public Login account() {
    Login account = new Login();
    account.setId(1231);
    return account;
  }

  @Bean
  public User userTest(Login account) {
    System.out.println(account.getId());
    return new User();
  }

  public static void main(String[] args) throws InterruptedException {
    @SuppressWarnings("resource")
    AnnotationConfigApplicationContext rootAppContext = new AnnotationConfigApplicationContext(ConfigTest.class);
    User user = (User) rootAppContext.getBean("userTest");
    System.out.println(user);
    Thread.sleep(5000);
  }
}
