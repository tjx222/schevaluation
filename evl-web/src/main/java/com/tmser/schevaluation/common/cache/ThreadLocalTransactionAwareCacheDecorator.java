/**
 * Mainbo.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */

package com.tmser.schevaluation.common.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;

/**
 * <pre>
 * 支持事务的缓存装饰器
 * </pre>
 *
 * @author tmser
 * @version $Id: ThreadLocalTransactionAwareCacheDecorator.java, v 1.0
 *          2017年10月24日 上午9:18:46 tmser Exp $
 */
public class ThreadLocalTransactionAwareCacheDecorator implements Cache {

  private final static Object DEAD_CACHE = new SimpleValueWrapper(null);

  private ThreadLocal<Map<Object, Object>> threadCache = new ThreadLocal<Map<Object, Object>>() {
    @Override
    protected Map<Object, Object> initialValue() {
      return new HashMap<Object, Object>();
    }
  };

  private final Cache targetCache;

  /**
   * Create a new ThreadLocalTransactionAwareCacheDecorator for the given target
   * Cache.
   * 
   * @param targetCache
   *          the target Cache to decorate
   */
  public ThreadLocalTransactionAwareCacheDecorator(Cache targetCache) {
    Assert.notNull(targetCache, "Target Cache must not be null");
    this.targetCache = targetCache;
  }

  @Override
  public String getName() {
    return this.targetCache.getName();
  }

  @Override
  public Object getNativeCache() {
    return this.targetCache.getNativeCache();
  }

  @Override
  public ValueWrapper get(Object key) {
    ValueWrapper value = null;
    if (TransactionSynchronizationManager.isSynchronizationActive()) {
      value = (ValueWrapper) threadCache.get().get(key);
      TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
        @Override
        public void afterCommit() {
          threadCache.remove();
        }
      });

      if (value != null) {
        return DEAD_CACHE == value ? null : value;
      }
    }
    return this.targetCache.get(key);
  }

  @Override
  public void put(final Object key, final Object value) {
    if (TransactionSynchronizationManager.isSynchronizationActive()) {
      threadCache.get().put(key, new SimpleValueWrapper(value));
      TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
        @Override
        public void afterCommit() {
          targetCache.put(key, value);
          threadCache.remove();
        }
      });
    } else {
      this.targetCache.put(key, value);
    }
  }

  @Override
  public void evict(final Object key) {
    if (TransactionSynchronizationManager.isSynchronizationActive()) {
      threadCache.get().put(key, DEAD_CACHE);
      TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
        @Override
        public void afterCommit() {
          targetCache.evict(key);
          threadCache.remove();
        }
      });
    } else {
      this.targetCache.evict(key);
    }
  }

  @Override
  public void clear() {
    this.targetCache.clear();
  }

  /**
   * @param key
   * @param type
   * @return
   * @see org.springframework.cache.Cache#get(java.lang.Object, java.lang.Class)
   */
  @SuppressWarnings("unchecked")
  @Override
  public <T> T get(Object key, Class<T> type) {
    ValueWrapper wv = get(key);
    if (wv != null) {
      Object value = wv.get();
      if (type.isInstance(value)) {
        return (T) value;
      } else if (value != null) {
        throw new IllegalStateException(
            "expect type : " + type.getName() + ", but value type : " + value.getClass().getName());
      }
    }
    return null;
  }

  /**
   * @param key
   * @param valueLoader
   * @return
   * @see org.springframework.cache.Cache#get(java.lang.Object,
   *      java.util.concurrent.Callable)
   */
  @SuppressWarnings("unchecked")
  @Override
  public <T> T get(Object key, Callable<T> valueLoader) {
    Object v;
    ValueWrapper value = null;
    if (TransactionSynchronizationManager.isSynchronizationActive()) {
      value = (ValueWrapper) threadCache.get().get(key);
      TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
        @Override
        public void afterCommit() {
          threadCache.remove();
        }
      });

      if (value != null) {
        v = value.get();
        return DEAD_CACHE == value ? null : (T) v;
      }
    }
    return this.targetCache.get(key, valueLoader);
  }

  /**
   * @param key
   * @param value
   * @return
   * @see org.springframework.cache.Cache#putIfAbsent(java.lang.Object,
   *      java.lang.Object)
   */
  @Override
  public ValueWrapper putIfAbsent(final Object key, final Object value) {
    if (TransactionSynchronizationManager.isSynchronizationActive()) {
      ValueWrapper wrapValue = get(key);
      if (wrapValue != null) {
        return wrapValue;
      }

      threadCache.get().put(key, new SimpleValueWrapper(value));
      TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
        @Override
        public void afterCommit() {
          targetCache.putIfAbsent(key, value);
          threadCache.remove();
        }
      });
    } else {
      return this.targetCache.putIfAbsent(key, value);
    }

    return null;
  }

}
