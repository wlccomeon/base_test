package com.lc.test.designpattern.abstractfactory;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * @author wlc
 * @desc: MarkDTO
 * @datetime: 2025/3/10 0010 16:19
 */
@Data
@Accessors(chain = true)
public class MarkDTO {

    private BigDecimal amt;

    private Integer day;

    private Integer month;

    private String custId;

}

