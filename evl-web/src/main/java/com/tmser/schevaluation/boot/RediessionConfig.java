/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */

package com.tmser.schevaluation.boot;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.codec.SerializationCodec;
import org.redisson.config.Config;
import org.redisson.spring.session.config.EnableRedissonHttpSession;
import org.springframework.context.annotation.Bean;

/**
 * <pre>
 * redission 配置
 * </pre>
 *
 * @author tmser
 * @version $Id: RediessionConfig.java, v 1.0 2017年12月19日 下午2:35:53 tmser Exp $
 */
@EnableRedissonHttpSession(maxInactiveIntervalInSeconds = 1200)
public class RediessionConfig {

  @Bean
  public RedissonClient redisson() {
    Config config = new Config();
    config.setUseLinuxNativeEpoll(false);
    config.setCodec(serializationCodec());
    config.setRedissonReferenceEnabled(true);
    config.useSentinelServers().setMasterName("mymaster").addSentinelAddress("redis://192.168.0.38:26379");
    return Redisson.create(config);
  }

  @Bean
  public Codec serializationCodec() {
    return new SerializationCodec();
  }
}
