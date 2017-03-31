package espace.data;

import espace.entity.Item;
import espace.enums.QuerySortOrder;

import java.util.List;
import java.util.Map;

public class ItemQueryData {

    private List<Item> result;
    private Integer totalResultCount;

    private int start;
    private int end;
    private String sortField;
    private QuerySortOrder order;
    private Map<String,Object> filters;

    public ItemQueryData(int start, int end, String field, QuerySortOrder order, Map<String, Object> filters) {

        this.start = start;
        this.end = end;
        this.sortField = field;
        this.order = order;
        this.filters = filters;
    }

    public List<Item> getResult() {
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

    public void setResult(List<Item> result) {
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
        return "QueryData [start=" + start + ", end=" + end + ", sortField=" + sortField + ", order=" + order + ", filters="
                + filters + "]";
    }

}
