//package com.lc.test.thread.lock.demo.countdownlatch;
//
//import com.google.common.base.Stopwatch;
//import com.hshc.datav.service.CusFlumeService;
//import com.hshc.datav.service.EmailUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.*;
//
///**
// * @author why
// * @version 1.0
// * @since 2019-11-06 10:30
// */
//@RestController
//public class MenuController implements InitializingBean {
//
//    private Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @RequestMapping(value = "/index")
//    public String index(Model model) {
//        return "index";
//    }
//
//    @Autowired
//    private CusFlumeService cusFlumeService;
//
//    @Value("${toMysql_activiti_explorer.replaceInsert.SaleAchievementArea}")
//    private String saleAchievementAreaSql;
//
//    private ExecutorService scheduleExec = new ThreadPoolExecutor(2, 2, 1, TimeUnit.MINUTES, new ArrayBlockingQueue<>(100),
//            new ThreadFactory() {
//                private final ThreadFactory delegate = Executors.defaultThreadFactory();
//
//                @Override
//                public Thread newThread(Runnable r) {
//                    Thread thread = delegate.newThread(r);
//                    thread.setName("修改成你的线程的名字");
//                    return thread;
//                }
//            }
//    );
//
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        //1.初始化
//        initData();
//        while (true) {
//            Boolean flag = true;
//            while (flag) {
//                try {
//                    CountDownLatch countDownLatch = new CountDownLatch(2);
//                    //2.计算
//                    scheduleExec.execute(() -> {
//                        try {
//                            calData();
//                        }finally {
//                            countDownLatch.countDown();
//                        }
//                    });
//                    //3.数据同步
//                    scheduleExec.execute(() -> {
//                        try {
//                            dtsData();
//                        }finally {
//                            countDownLatch.countDown();
//                        }
//                    });
//                    countDownLatch.await();
//					logger.info("开始数据计算 .......");
//					int num = cusFlumeService.insertDataV();
//					logger.info("insertDataV当前受影响行数：" + num);
//
//            } catch(Exception e){
//                flag = false;
//                sendEmail(flag, e.toString());//发送预警
//            }
//        }
//        scheduleExec.shutdown();
//    }
//
//    /**
//     * 初始化
//     */
//    private void initData(){
//        try {
//
//            logger.info("初始化开始......");
//            Stopwatch initWatch = Stopwatch.createStarted();
//
//            // Todo 初始result表
//            int delResultNum = cusFlumeService.delDataV();
//            logger.info("result表已清空，{}条", delResultNum);
//
//            int resultNum = cusFlumeService.insertDataV();
//            logger.info("result表初始{}条", resultNum);
//
//            // Todo 初始化bak表
//            int deleteBakNum = cusFlumeService.deleteDataVBak();
//            logger.info("bak表删除成功{}条", deleteBakNum);
//
//            int insertBakNum = cusFlumeService.insertDataVBak();
//            logger.info("bak表初始化{}条", insertBakNum);
//
//            // Todo 初始化mysql表
//            // 初始化同步Bak表中数据到画像库中的Mysql表
//            //List<Map<String,String>> resultBakNumSs = cusFlumeService.selectBakTotalNum();
//            //logger.info("mysql result表{}条", resultBakNumSs.size());
//
//            // 同一个事务: 1、清空Mysql表 2、插入Mysql表
//            String resultSql = "replace into export_hshc_20191111_result(order_no,order_id,product_line,source,sys_type,term,series_name,sale_store_name,area,sale_store_province,sale_store_city,enter_time,contract_time,delivery_time,delivery_pass_time,view_flag,sale_store_code,branch,funder,ord_status,cancel_time)\n" +
//                    " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//            //cusFlumeService.replaceIntoMysqlResult(resultBakNumSs,resultSql);
//            //logger.info("mysql result表插入完成{}条", resultBakNumSs.size());
//
//            //分片同步bak表至mysql
//            int resultBakNumS = cusFlumeService.dtsToMysql(resultSql,5000);//分片同步
//
//            // 初始化完成，result表条数、bak表条数、mysql表条数、初始化耗时
//            logger.info("初始化已完成(*^▽^*) result表条数{}、bak表条数{}、mysql表条数{}、初始化耗时{}ms",resultNum,insertBakNum,resultBakNumS,initWatch.stop().elapsed(TimeUnit.MILLISECONDS));
//
//            //TODO:发送邮件
//            //sendEmail(false,"发送完成");
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 计算
//     */
//    private void calData(){
//
//        Stopwatch calWatch = Stopwatch.createStarted();
//
//        //接口1：当日总进件量、当日总签约量、当日总交车量。行数：1
//        List<Map<String,String>> totalNumS = cusFlumeService.selectInterfaceOrderNoTotalNum();
//        String totalNumSql="replace into interface_order_no_total_num(idx,enter_num,sign_num,dlvy_num) values(?,?,?,?)";
//        cusFlumeService.replaceIntoTotalNum(totalNumS,totalNumSql);
//
//        //接口2：分产品线的 当日进件量、当日签约量。行数：3
//        List<Map<String,String>> productLineNumS = cusFlumeService.selectInterfaceOrderNoByProductLineNum();
//        String productLineNumSql="replace into interface_order_no_by_product_line_num(idx, product_line, enter_num, sign_num) values(?,?,?,?)";
//        cusFlumeService.replaceIntoProductLineNum(productLineNumS,productLineNumSql);
//
//        //接口3：分车系+渠道的 当日签约量（车系签约量Top5）。行数：30
//        List<Map<String,String>> sourceNumS = cusFlumeService.selectInterfaceOrderNoByTop5SeriesAndSourceNum();
//        String sourceNumSql="replace into interface_order_no_by_top5_series_and_source_num(idx, idx_fir, series_name, series_sign_num, idx_sec, source, series_source_sign_num) values(?,?,?,?,?,?,?)";
//        cusFlumeService.replaceIntoSourceNum(sourceNumS,sourceNumSql);
//
//
//        //接口4：分省份的 当日签约量。行数：5
//        List<Map<String,String>> provinceNumS = cusFlumeService.SelectInterfaceOrderNoByProvinceNum();
//        String provinceNumSql="replace into interface_order_no_by_province_num(idx, province_name, sign_num) values(?,?,?)";
//        cusFlumeService.replaceIntoProvinceNum(provinceNumS,provinceNumSql);
//
//
//        //接口5：分城市的 当日签约量。行数：8
//        List<Map<String,String>> cityNumS = cusFlumeService.SelectInterfaceOrderNoByCityNum();
//        String cityNumSql="replace into interface_order_no_by_city_num(idx, city_name, sign_num) values(?,?,?)";
//        cusFlumeService.replaceIntoCityNum(cityNumS,cityNumSql);
//
//        //接口6：分渠道的 当日签约量。行数：6
//        List<Map<String,String>> orderNoBySourceNumS = cusFlumeService.SelectInterfaceOrderNoBySourceNum();
//        String orderNoBySourceNumSql="replace into interface_order_no_by_source_num(idx, source, sign_num) values(?,?,?)";
//        cusFlumeService.replaceIntoOrderNoBySourceNum(orderNoBySourceNumS,orderNoBySourceNumSql);
//
//
//        //接口7：分购车方式的 当日签约量。行数：2
//        List<Map<String,String>> payModeNumS = cusFlumeService.SelectInterfaceOrderNoByPayModeNum();
//        String payModeNumSql="replace into interface_order_no_by_pay_mode_num(idx, pay_mode, sign_num) values(?,?,?)";
//        cusFlumeService.replaceIntoPayModeNum(payModeNumS,payModeNumSql);
//
//
//        //接口8：分战区的 当日签约量。行数：19
//        List<Map<String,String>> areaNumS = cusFlumeService.SelectInterfaceOrderNoByAreaNum();
//        String areaNumSql="replace into interface_order_no_by_area_num(idx, idx_fir, region_name, idx_sec, process_type, num) values(?,?,?,?,?,?)";
//        cusFlumeService.replaceIntoAreaNum(areaNumS,areaNumSql);
//
//
//        //接口9：分门店的 当日进件量、当日签约量、经纬度。行数：历史所有门店数
//        List<Map<String,String>> storeNumS = cusFlumeService.SelectInterfaceOrderNoByStoreNum();
//        String storeNumSql="replace into interface_order_no_by_store_num(idx, store_name, lat, lng, enter_num, sign_num) values(?,?,?,?,?,?)";
//        cusFlumeService.replaceIntoStoreNum(storeNumS,storeNumSql);
//
//        //接口10：
//        List<Map<String,String>> detailInfoS = cusFlumeService.selectInterfaceOrderNoSignDetailInfo();
//        String detailInfoSql="replace into interface_order_no_sign_detail_info(idx, order_no, store_name, lat, lng, num) values(?,?,?,?,?,?)";
//        cusFlumeService.replaceIntoDetailInfo(detailInfoS,detailInfoSql);
//
//        //接口11：
//        List<Map<String,String>> firPayTotalNums = cusFlumeService.selectInterfaceOrderNoFirPayTotalNum();
//        String firPayTotalNumSql="replace into interface_order_no_fir_pay_total_num(idx, product_type, fir_pay_num) values(?,?,?)";
//        cusFlumeService.replaceIntofirPayTotalNum(firPayTotalNums,firPayTotalNumSql);
//
//        //TODO：查询战区
//        List<Map<String,String>> areaList = cusFlumeService.selectSaleAchievementArea();
//
//        //战区同步to mysql
//        cusFlumeService.replaceIntoSaleAchievementArea(areaList,saleAchievementAreaSql);
//        logger.info("战区同步to mysql已完成，{}条",areaList.size());
//
//        cusFlumeService.replaceIntoSaleAchievementBranch();
//        logger.info("企业微信小时级报表2同步完成……");
//
//        cusFlumeService.replaceIntoSaleAchievementStore();
//        logger.info("企业微信小时级报表同步完成……");
//
//        cusFlumeService.replaceIntoSaleAgentStoreAchievementStoreDa();
//        logger.info("企业微信小时级报表-加盟同步完成……");
//
//        logger.info("计算模块已完成，耗时{}ms",calWatch.stop().elapsed(TimeUnit.MILLISECONDS));
//
//    }
//
//    /**
//     * 数据同步
//     */
//    private void dtsData(){
//        logger.info("开始数据同步 .......");
//        Stopwatch dtsWatch = Stopwatch.createStarted();
//
//        //TODO:接口0：result表与bak表的增量数据
//        Stopwatch toMysqlwatch = Stopwatch.createStarted();
//        List<Map<String,String>> rltBakDiffNumS = cusFlumeService.selectResultTotalNum();
//        logger.error("result表与bak表的增量数据{}条"+rltBakDiffNumS.size());
//
//        String toMysql = "replace into export_hshc_20191111_result(order_no,order_id,product_line,source,sys_type,term,series_name,sale_store_name,area,sale_store_province,sale_store_city,enter_time,contract_time,delivery_time,delivery_pass_time,view_flag,sale_store_code,branch,funder,ord_status,cancel_time)\n" +
//                " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//        cusFlumeService.replaceIntoMysqlResult(rltBakDiffNumS,toMysql);
//        logger.info("result表与bak表的增量数据同步至MySQL完成,{}ms", toMysqlwatch.stop().elapsed(TimeUnit.MILLISECONDS));
//
//        //TODO:清空resultBak表，保持result表与resultBak表一致
//        int deleteNum = cusFlumeService.deleteDataVBak();
//        logger.info("resultBak表清空完成" + deleteNum);
//
//        int bakNum = cusFlumeService.insertDataVBak();
//        logger.info("resultBak表插入{}条" + bakNum);
//
//        logger.info("数据同步模块已完成，增量同步{}条，bak表{}条，耗时{}ms",rltBakDiffNumS.size(),bakNum ,dtsWatch.stop().elapsed(TimeUnit.MILLISECONDS));
//
//    }
//
//    /**
//     * 预警发送
//     * @param flag
//     * @param erroMsg
//     */
//    private void sendEmail(Boolean flag,String erroMsg){
//        while (!flag) {
//            EmailUtil emailUtil = new EmailUtil();
//            HashMap<String, String> inputParams = new HashMap<>();
//            inputParams.put("subject", "测试主题"); // 主题
//            inputParams.put("content", "双十一大屏数据异常报警:" + erroMsg); // 正文
//            inputParams.put("to", "sauna@huashenghaoche.com;tongyuezhao@huashenghaoche.com"); // 收件人
//            inputParams.put("cc", "huayingwang@huashenghaoche.com"); // 抄送人列表
//            inputParams.put("sender_addr", "tongyuezhao@huashenghaoche.com"); // 发件人邮箱
//            inputParams.put("sender_passwd", "zGXddsATY9eyPKr2"); // 发件人密码
//            inputParams.put("sender_name", "信息技术中心-算法组"); // 发件人显示名称
//            inputParams.put("reply_to", "sauna@huashenghaoche.com"); // 回复地址
//            inputParams.put("attach_name", ""); // 附件路径，以分号分隔
//
//            emailUtil.sendTextEmail(inputParams);
//
//            try {
//                Thread.sleep(10000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            flag = true;
//        }
//    }
//
//}
