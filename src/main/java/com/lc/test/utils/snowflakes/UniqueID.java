package com.lc.test.utils.snowflakes;

import com.lc.test.utils.LocalHostUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wlc
 * @desc
 * @date 2019-12-09 20:59:59
 */
public class UniqueID {
	private static Logger logger = LoggerFactory.getLogger(UniqueID.class);

	private static IdWorker idWorder;


	static {
		int machineNo = Integer.parseInt(LocalHostUtil.getLastSegment()); // 机器唯一标识号
		//第2个参数可根据需求修改
		idWorder = new IdWorker(machineNo,machineNo);
		logger.info("当前运行机器LastSegmentIP:" + machineNo);
	}

	public static synchronized long generateId() {
		return idWorder.nextId();
	}
}
