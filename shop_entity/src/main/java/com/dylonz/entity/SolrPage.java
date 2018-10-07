package com.dylonz.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SolrPage<T> {

    private Integer currentPage=1;

    private Integer pageSize=10;

    private Integer totalCount;

    private Integer totalPage;

    private List<T> datas;
}
