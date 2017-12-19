/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */

package com.mainbo.jy.common.cache.ehcache;

import java.util.Collection;
import java.util.LinkedHashSet;

import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.transaction.AbstractTransactionSupportingCacheManager;
import org.springframework.util.Assert;

import com.mainbo.jy.common.cache.ThreadLocalTransactionAwareCacheDecorator;

import net.sf.ehcache.Status;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tmser
 * @version $Id: Snippet.java, v 1.0 2016年10月18日 上午9:20:35 tmser Exp $
 */
public class EhCacheManager extends AbstractTransactionSupportingCacheManager {

  private net.sf.ehcache.CacheManager cacheManager;

  /**
   * Create a new EhCacheCacheManager, setting the target EhCache CacheManager
   * through the {@link #setCacheManager} bean property.
   */
  public EhCacheManager() {
  }

  /**
   * Create a new EhCacheCacheManager for the given backing EhCache
   * CacheManager.
   * 
   * @param cacheManager
   *          the backing EhCache {@link net.sf.ehcache.CacheManager}
   */
  public EhCacheManager(net.sf.ehcache.CacheManager cacheManager) {
    this.cacheManager = cacheManager;
  }

  /**
   * Set the backing EhCache {@link net.sf.ehcache.CacheManager}.
   */
  public void setCacheManager(net.sf.ehcache.CacheManager cacheManager) {
    this.cacheManager = cacheManager;
  }

  /**
   * Return the backing EhCache {@link net.sf.ehcache.CacheManager}.
   */
  public net.sf.ehcache.CacheManager getCacheManager() {
    return this.cacheManager;
  }

  @Override
  protected Collection<Cache> loadCaches() {
    Assert.notNull(this.cacheManager, "A backing EhCache CacheManager is required");
    Status status = this.cacheManager.getStatus();
    Assert.isTrue(Status.STATUS_ALIVE.equals(status),
        "An 'alive' EhCache CacheManager is required - current cache is " + status.toString());

    String[] names = this.cacheManager.getCacheNames();
    Collection<Cache> caches = new LinkedHashSet<Cache>(names.length);
    for (String name : names) {
      caches.add(new Ehcache(this.cacheManager.getEhcache(name)));
    }
    return caches;
  }

  @Override
  protected Cache getMissingCache(String name) {
    net.sf.ehcache.Ehcache ehcache = this.cacheManager.getEhcache(name);
    if (ehcache != null) {
      return new EhCacheCache(ehcache);
    }
    return null;
  }

  @Override
  protected Cache decorateCache(Cache cache) {
    return (isTransactionAware() ? new ThreadLocalTransactionAwareCacheDecorator(cache) : cache);
  }

}
