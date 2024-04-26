package com.lc.test.baseknowlege.object;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wlc
 * @desc: WzTaxAuthResp
 * @datetime: 2024/4/24 0024 9:27
 */
@Data
public class WzTaxAuthResp  implements Serializable {
    /**
     * 返回标记：成功标记=0，失败标记=-1
     */
    private String code;
    /**
     * 返回信息
     */
    private String msg;
    /**
     * 平台错误信息
     */
    private String title;
    /**
     * 数据
     */
    private AuthSummary data;

    @Data
    class AuthSummary {
        /**
         * 授权链接
         */
        private String authUrl;
        /**纳税人识别号（统一社会信用代码）*/
        private String nsrsbh;
        /**订单明细，如该企业未认证成功，将会匹配输出当月该企业的认证记录*/
        private List<AuthData> authList;
    }

    @Data
    class AuthData {
        /**
         * 该ID 对应唯一一笔订单；认证成功后生成并返回该值
         */
        private String authId;
        /**
         * 认证成功，为订单时间；
         *
         * 认证失败，为认证请求时间；
         */
        private String authTime;
        /**
         * 认证成功为订单状态（1000 认证成功，其余详见订单状态 4.2 结果码描述）；
         *
         * 认证失败为认证状态；
         */
        private String orderStatus;
        /**
         * 认证成功为订单结果描述信息；
         */
        private String orderMsg;
        /**
         * 渠道来源（pc，mobile）
         */
        private String channel;
        /**
         * 返回参数reportInfos 节点
         */
        private List<ReportInfo> reportInfos;
    }
    @Data
    class ReportInfo{
        /**
         * 报告Id
         */
        private String reportId;
        /**
         * 报告key
         */
        private String key;
        /**
         * 报告状态（4001 报告生成中 4000 报告成功 4009 报告失败）
         */
        private String reportStatus;
        /**
         * 备注
         */
        private String remark;
    }
}

