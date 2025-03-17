package com.lc.test.designpattern.abstractfactory;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * @author wlc
 * @desc: NormalInspection
 * @datetime: 2025/3/10 0010 16:18
 */
@Order(3)
@Service
public class NormalInspection implements AbstractInspection{

    @Override
    public String mark(MarkDTO markDTO) {
        if (markDTO.getDay() == 0){
            return "正常项目类";
        }
        return null;
    }
}

