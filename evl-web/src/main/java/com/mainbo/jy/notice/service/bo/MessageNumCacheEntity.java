package com.mainbo.jy.notice.service.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.async.DeferredResult;

import com.mainbo.jy.common.vo.Result;

/**
 * 
 * <pre>
 * 通知与站内信未读数目集合
 * </pre>
 *
 * @author wanzheng
 * @version $Id: MessageNumCache.java, v 1.0 May 12, 2015 1:38:59 PM wanzheng Exp $
 */
public class MessageNumCacheEntity {

	/**
	 * 消息数目等待返回
	 */
	private List<DeferredResult<Result>> results = new ArrayList<DeferredResult<Result>>();
	
	/**
	 * 通知最近更新时间
	 */
	private long noticeTimeStamp;
	
	/**
	 * 通知数目
	 */
	private int noticeNum = 0;
	
	/**
	 * 新增通知数目
	 * @param num
	 */
	public void increaseNotice(Integer num){
		synchronized (results) {
			noticeNum = noticeNum + num;
			if(noticeNum < 0)
				noticeNum = 0;
			noticeTimeStamp=new Date().getTime();
			pushNoticeNum();
		}
	}
	
	
	/**
	 * 推送通知数目
	 */
	private void pushNoticeNum() {
		if(CollectionUtils.isEmpty(results)){
			return;
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("noticeTimeStamp",noticeTimeStamp);
		map.put("noticeNum",noticeNum);
		Iterator<DeferredResult<Result>> it = results.iterator();
		while (it.hasNext())
		{
			DeferredResult<Result> result = it.next();
			if(!result.isSetOrExpired()){
				result.setResult(new Result(map));
			}
			it.remove();
		}
	}
	
	/**
	 * 新增异步结果
	 * @param lastTimeStamp 更新消息时间
	 * @param asyncResult
	 */
	public void addResult(Long lastTimeStamp,final DeferredResult<Result> asyncResult){
		if(asyncResult == null){
			return;
		}
		
		asyncResult.onTimeout(new Runnable() {
			@Override
			public void run() {
				synchronized (results) {
					results.remove(asyncResult);
				}
			}
		});
		synchronized (results) {
			if(lastTimeStamp==null || noticeTimeStamp>lastTimeStamp){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("noticeTimeStamp",noticeTimeStamp);
				map.put("noticeNum",noticeNum);
				asyncResult.setResult(new Result(map));
			}else{
				results.add(asyncResult);
			}
		}
	}
	
}
