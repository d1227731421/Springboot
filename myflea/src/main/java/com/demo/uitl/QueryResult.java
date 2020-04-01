package com.demo.uitl;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

@ToString
@Data
public class QueryResult<T> {
    //数据列表
    private List<T> list=new ArrayList<> ();
    //数据总数
    private long total=0;
    public QueryResult() {
    }
    public QueryResult(List<T> list, long total) {
        this.list = list;
        this.total = total;
    }
}
