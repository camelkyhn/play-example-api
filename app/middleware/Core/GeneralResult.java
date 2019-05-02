package middleware.Core;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class GeneralResult<T> extends BaseResult {

    private T data;

    private Pagination pagination;

    public GeneralResult() {
    }

    public GeneralResult(String exceptionType, String exceptionMessage) {
        setSucceeded(false);
        setExceptionType(exceptionType);
        setExceptionMessage(exceptionMessage);
    }

    public void PaginationInfo(int pageSize, int pageNumber, int totalCount) {
        setPagination(new Pagination(pageSize, pageNumber, totalCount));
    }

    public void Success(T data) {
        setSucceeded(true);
        setData(data);
    }

    public void Success(T data, Pagination pagination) {
        setSucceeded(true);
        setData(data);
        setPagination(pagination);
    }

    @JsonIgnore
    public boolean isSuccess() {
        return data != null && isSucceeded();
    }

    @JsonIgnore
    public boolean isFailed() {
        return data == null || !isSucceeded();
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
