package com.lc.test.entity;

import lombok.Data;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Date;

/**
 * Created by wlc on 2017/8/15 0015.
 */
@Data
public class Tender {
    private Integer id;
    private Integer uid;
    private String description;
    private Date createDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Tender tender = (Tender) o;

        return new EqualsBuilder()
                .append(id, tender.id)
                .append(uid, tender.uid)
                .append(description, tender.description)
                .append(createDate, tender.createDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(uid)
                .append(description)
                .append(createDate)
                .toHashCode();
    }
}
