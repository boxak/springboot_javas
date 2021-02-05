package com.exercise.javas.utils;

public class PagingControl {
    private static final int POST_CNT_PER_PAGE = 9;
    private static final int PAGE_UNIT = 5;
    private long totalPostCnt;
    private int curPage;

    public PagingControl(int curPage, long totalPostCnt) {
        this.curPage = curPage;
        this.totalPostCnt = totalPostCnt;
    }

    public long getTotalPage() {
        return ((totalPostCnt - 1) / POST_CNT_PER_PAGE) + 1;
    }

    public int getStartPage() {
        return ((curPage - 1) / PAGE_UNIT) * PAGE_UNIT + 1;
    }

    public int getEndPage() {
        return (int)Math.min(getStartPage() + PAGE_UNIT - 1, getTotalPage());
    }

    public boolean hasPreData() {
        return getStartPage() != 1;
    }

    public boolean hasNextData() {
        return getEndPage() < getTotalPage();
    }
}
