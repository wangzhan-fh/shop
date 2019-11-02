package com.fh.shop.api.conmmons;

import java.io.Serializable;
import java.util.List;

public class DataTableResult implements Serializable {

    private Integer draw;
    private Long recordsFiltered;
    private Long recordsTotal;
    private List data;

    public DataTableResult(){}

    public DataTableResult(Integer draw, Long recordsFiltered, Long recordsTotal, List data) {
        this.draw = draw;
        this.recordsFiltered = recordsFiltered;
        this.recordsTotal = recordsTotal;
        this.data = data;
    }

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Long getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(Long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public Long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(Long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }
}
