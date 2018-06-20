package com.jt.order.job;

import java.util.Date;

import org.joda.time.DateTime;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.jt.order.mapper.OrderMapper;
// Spring整合Quarz
public class PaymentOrderJob extends QuartzJobBean{

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		// 从定时任务中获取Spring容器
		ApplicationContext applicationContext = (ApplicationContext) context.getJobDetail().getJobDataMap().get("applicationContext");
		/**
		 * 通过Spring容器获取OrderMapper对象，通过Ordermapper对象修改超时状态
		 * 根据超时时间将状态修改为6
		 * 超时时间节点= 当前时间-24小时(1天)
		 */
		OrderMapper orderMapper = applicationContext.getBean(OrderMapper.class);
		DateTime dateTime = new DateTime();
		// time表示超时时间
		Date time = dateTime.minusDays(1).toDate();
		orderMapper.updateStatusByDate(time);
		
	}
	
	
}
