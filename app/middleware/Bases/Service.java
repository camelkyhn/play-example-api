package middleware.Bases;

import io.ebean.ExpressionList;
import io.ebean.Finder;

import java.util.List;

public class Service<TKeyType, TEntity> {

    protected Finder<TKeyType, TEntity> finder;

    private Class<TEntity> entityType;

    public Service(Class<TEntity> type) {
        entityType  = type;
        this.finder = new Finder<>(entityType);
    }

    public List<TEntity> applyBaseFilter(BaseFilter filter, ExpressionList<TEntity> expressionList) {
        if (filter.getDateBefore() != null) {
            expressionList = expressionList.where().le("updatedDate", filter.getDateBefore());
        }

        if (filter.getDateAfter() != null) {
            expressionList = expressionList.where().ge("updatedDate", filter.getDateAfter());
        }

        if (filter.getStatus() != null) {
            expressionList = expressionList.where().eq("status", filter.getStatus());
        }

        if (filter.getUpdatedUserEmail() != null && !filter.getUpdatedUserEmail().isEmpty()) {
            expressionList = expressionList.where().contains("updatedUser.email", filter.getUpdatedUserEmail());
        }

        List<TEntity> list = expressionList.findList();
        filter.setTotalCount(list.size());
        if (filter.isAllData()) {
            return list;
        }

        if (filter.getPageSize() > 0 && filter.getPageNumber() > 0 && list.size() >= filter.getPageSize()) {
            list = list.subList((filter.getPageNumber() - 1) * filter.getPageSize(), filter.getPageSize() - 1);
        }

        return list;
    }
}
