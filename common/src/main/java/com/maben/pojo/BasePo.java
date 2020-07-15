package com.maben.pojo;

/**
 * @description:
 * @author: maben
 * @createDate: 2020/4/9
 */
public class BasePo {
    final static Integer PAGE_LIMIT_DEFAULT = 1;

    private Integer currentPage;
    private Integer pageLimit;


    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageLimit() {
        if (pageLimit == null) {
            return BasePo.PAGE_LIMIT_DEFAULT;
        }
        return pageLimit;
    }

    public void setPageLimit(Integer pageLimit) {
        this.pageLimit = pageLimit;
    }
}
