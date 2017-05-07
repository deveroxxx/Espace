package espace.data;

import espace.enums.QuerySortOrder;

import java.util.List;
import java.util.Map;

public class QueryData<T> {

    private List<T> result;
    private Integer totalResultCount;

    private int start;
    private int end;
    private String sortField;
    private QuerySortOrder order;
    private Map<String,Object> filters;
    private Class<T> clazz;
    private String clazzName;

    public QueryData(int start, int end, String field, QuerySortOrder order, Map<String, Object> filters) {
        this.clazz = null;//(Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.clazzName = this.clazz == null ? "" : this.clazz.getSimpleName();
        this.start = start;
        this.end = end;
        this.sortField = field;
        this.order = order;
        this.filters = filters;
    }

    public List<T> getResult() {
        return result;
    }

    public Integer getTotalResultCount() {
        return totalResultCount;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public QuerySortOrder getOrder() {
        return order;
    }

    public Map<String, Object> getFilters() {
        return filters;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public void setTotalResultCount(Integer totalResultCount) {
        this.totalResultCount = totalResultCount;
    }

    public String getSortField() {
        return sortField;
    }

    @Override
    public String toString() {
        return clazzName + "-QueryData [start=" + start + ", end=" + end + ", sortField=" + sortField + ", order=" + order + ", filters="
                + filters + "]";
    }

}
