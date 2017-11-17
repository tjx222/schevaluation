/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */

package redission.test;

import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: RedissionTest.java, v 1.0 2017年11月7日 上午9:19:21 tmser Exp $
 */
public class RedissionTest {

  @Test
  public void testSentinel() {
    Config config = new Config();
    config.useSentinelServers().setMasterName("mymaster")
        // 可以用"rediss://"来启用SSL连接
        .addSentinelAddress("redis://192.168.0.38:26379");

    RedissonClient redisson = Redisson.create(config);

  }
}
