package middleware.Bases;

import middleware.Enums.Status;

import javax.annotation.Nullable;
import java.util.Date;

public class BaseFilter {

    private int pageSize = 5;

    private int pageNumber = 1;

    private int totalCount;

    private boolean isAllData;

    @Nullable
    private Date dateBefore;

    @Nullable
    private Date dateAfter;

    private String updatedUserEmail;

    @Nullable
    private Status status;

    public BaseFilter() {
    }

    public int getPageSize() {
        if (pageSize <= 5) {
            return 5;
        }
        else {
            return pageSize;
        }
    }

    public void setPageSize(int pageSize) {
        if (pageSize <= 5) {
            this.pageSize = 5;
        }
        else {
            this.pageSize = pageSize;
        }
    }

    public int getPageNumber() {
        if (pageNumber <= 1) {
            return 1;
        }
        else {
            return pageNumber;
        }
    }

    public void setPageNumber(int pageNumber) {
        if (pageNumber <= 1) {
            this.pageNumber = 1;
        }
        else {
            this.pageNumber = pageNumber;
        }
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public boolean isAllData() {
        return isAllData;
    }

    public void setAllData(boolean allData) {
        isAllData = allData;
    }

    public Date getDateBefore() {
        return dateBefore;
    }

    public void setDateBefore(Date dateBefore) {
        this.dateBefore = dateBefore;
    }

    public Date getDateAfter() {
        return dateAfter;
    }

    public void setDateAfter(Date dateAfter) {
        this.dateAfter = dateAfter;
    }

    public String getUpdatedUserEmail() {
        return updatedUserEmail;
    }

    public void setUpdatedUserEmail(String updatedUserEmail) {
        this.updatedUserEmail = updatedUserEmail;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
