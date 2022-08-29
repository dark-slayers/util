package person.liuxx.util.service.reponse;

import java.util.List;
import java.util.stream.Collectors;

public class PageResponse<T> {
    /**
     * 总记录数
     */
    private final long totalCount;
    /**
     * 每页记录数
     */
    private final long pageSize;
    /**
     * 总页数
     */
    private final long totalPage;
    /**
     * 当前页数
     */
    private final long currPage;
    /**
     * 列表数据
     */
    private final List<T> list;

    /**
     * @param sourceList 列表数据
     * @param pageSize 每页记录数
     * @param currPage 当前页数——从1开始计算
     */
    public PageResponse(List<T> sourceList, int pageSize, int currPage) {
        this.pageSize = pageSize;
        this.currPage = currPage;
        totalCount = sourceList.size();
        totalPage = (int) Math.ceil((double) totalCount / pageSize);
        long skipNumber = (currPage - 1) * pageSize;
        list = sourceList.stream()
                .skip(skipNumber)
                .limit((long) pageSize)
                .collect(Collectors.toList());
    }

    /**
     * 使用列表和参数创建分页，此构造器使用的列表为单页列表
     * 
     * @param sourceList
     *            单页列表
     * @param totalCount
     *            总记录数
     * @param pageSize
     *            每页记录数
     * @param currPage
     *            当前页数 ——从1开始计算
     */
    public PageResponse(List<T> sourceList, long totalCount, long pageSize, long currPage) {
        this.pageSize = pageSize;
        this.currPage = currPage;
        this.totalCount = totalCount;
        totalPage = (long) Math.ceil((double) totalCount / pageSize);
        list = sourceList;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public long getPageSize() {
        return pageSize;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public long getCurrPage() {
        return currPage;
    }

    public List<?> getList() {
        return list;
    }
}