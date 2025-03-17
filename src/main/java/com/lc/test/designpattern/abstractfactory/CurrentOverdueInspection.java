package com.lc.test.designpattern.abstractfactory;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * @author wlc
 * @desc: CurrentOverdueInspection
 * @datetime: 2025/3/10 0010 16:18
 */
@Order(1)
@Service
public class CurrentOverdueInspection implements AbstractInspection{

    @Override
    public String mark(MarkDTO markDTO) {
        if (markDTO.getDay() > 0){
            return "当前逾期";
        }
        return null;
    }
}

