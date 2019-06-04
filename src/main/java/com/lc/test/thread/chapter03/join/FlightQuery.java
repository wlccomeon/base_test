package com.lc.test.thread.chapter03.join;

import java.util.List;

/**
 * 多个子线程并行查询多个航班
 * @author wlc
 */
public interface FlightQuery {
	List<String> get();
}
