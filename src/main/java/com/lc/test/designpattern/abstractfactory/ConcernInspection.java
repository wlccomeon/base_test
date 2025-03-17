package com.lc.test.designpattern.abstractfactory;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * @author wlc
 * @desc: ConcernInspection
 * @datetime: 2025/3/10 0010 16:26
 */
@Order(2)
@Service
public class ConcernInspection implements AbstractInspection{
    @Override
    public String mark(MarkDTO markDTO) {
        if (StringUtils.isNotBlank(markDTO.getCustId())){
            return "授信关注类";
        }
        return null;
    }
}

