/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */

package com.mainbo.jy.common.cache.ehcache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.cache.transaction.AbstractTransactionSupportingCacheManager;
import org.springframework.util.Assert;

/**
 * <pre>
 *  ehcache 多管理器适配
 * </pre>
 *
 * @author tmser
 * @version $Id: CompositeEhCacheManager.java, v 1.0 2015年2月7日 下午5:08:54 tmser
 *          Exp $
 */
public class CompositeEhCacheManager extends AbstractTransactionSupportingCacheManager
    implements InitializingBean, CacheManager {

  private List<CacheManager> cacheManagers;
  private CacheManager noOpCacheManager = new NoOpCacheManager();

  private boolean fallbackToNoOpCache = false;

  /**
   * set cachemangers
   * 
   * @param cacheManagers
   *          cm
   */
  public void setCacheManagers(Collection<CacheManager> cacheManagers) {
    Assert.notEmpty(cacheManagers, "cacheManagers Collection must not be empty");
    this.cacheManagers = new ArrayList<CacheManager>();
    this.cacheManagers.addAll(cacheManagers);
  }

  /**
   * Indicate whether a {@link NoOpCacheManager} should be added at the end of
   * the manager lists.
   * In this case, any {@code getCache} requests not handled by the configured
   * cache managers will
   * be automatically handled by the {@link NoOpCacheManager} (and hence never
   * return {@code null}).
   */
  public void setFallbackToNoOpCache(boolean fallbackToNoOpCache) {
    this.fallbackToNoOpCache = fallbackToNoOpCache;
  }

  @Override
  public Cache getCache(String name) {
    if (this.fallbackToNoOpCache) {
      return noOpCacheManager.getCache(name);
    }
    return super.getCache(name);
  }

  @Override
  protected Cache getMissingCache(String name) {
    Cache cache = null;
    for (CacheManager cacheManager : this.cacheManagers) {
      cache = cacheManager.getCache(name);
      if (cache != null) {
        break;
      }
    }
    return cache;
  }

  @Override
  protected Collection<Cache> loadCaches() {
    Collection<Cache> caches = new LinkedHashSet<Cache>();
    for (CacheManager cacheManager : this.cacheManagers) {
      // Status status = cacheManager.getStatus();
      // Assert.isTrue(Status.STATUS_ALIVE.equals(status),
      // "An 'alive' EhCache CacheManager is required - current cache is " +
      // status.toString());

      Collection<String> names = cacheManager.getCacheNames();
      for (String name : names) {
        caches.add(cacheManager.getCache(name));
      }
    }
    return caches;
  }
}
