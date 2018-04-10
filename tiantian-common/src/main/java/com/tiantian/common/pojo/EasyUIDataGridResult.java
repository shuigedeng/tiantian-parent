package com.tiantian.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author 戴礼明
 * @create 2018/4/10 14:31
 */
public class EasyUIDataGridResult implements Serializable{
    private int total;

    private List<?> rows;

    public EasyUIDataGridResult(Integer total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }

    public EasyUIDataGridResult(Long total, List<?> rows) {
        this.total = total.intValue();
        this.rows = rows;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

}
