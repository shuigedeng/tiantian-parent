package com.tiantian.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author 戴礼明
 * @create 2018/4/10 14:31
 */
public class EasyUIDataGridResult implements Serializable{
    private Long total;

    private List<?> rows;

    public EasyUIDataGridResult() {
    }

    public EasyUIDataGridResult(Long total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public List<?> getRows() {
        return rows;
    }
}
