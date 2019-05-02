package middleware.Core;

public class Pagination {

    private int pageSize;

    private int pageNumber;

    private int totalCount;

    public Pagination() {
    }

    public Pagination(int pageSize, int pageNumber, int totalCount) {
        this.pageSize   = pageSize;
        this.pageNumber = pageNumber;
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
