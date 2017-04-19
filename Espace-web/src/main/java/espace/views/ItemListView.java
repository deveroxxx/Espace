package espace.views;

import espace.data.ItemQueryData;
import espace.entity.Item;
import espace.entity.ItemCategory;
import espace.enums.QuerySortOrder;
import espace.managers.ItemManager;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "dtItemList")
@RequestScoped
public class ItemListView implements Serializable {

    @Inject
    ItemManager itemManager;


    private LazyDataModel<Item> lazyModel;
    private Item selectedItem;

    private List<Item> currentListOfItems;
    private List<ItemCategory> itemCategoryList;


    @PostConstruct
    public void init() {
        //TODO: legördülőket majd itt initelni ha lesznek
    }


    public LazyDataModel<Item> getLazyModel() {

        if (lazyModel == null) {
            lazyModel = new LazyDataModel<Item>() {

                @Override
                public List<Item> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                    //log.info("first=" + first + ", pagesize=" + pageSize + ", sortfield=" + sortField + " sortorder=" + sortOrder + " filter:" + filters);
                    int start = first;
                    int end = first + pageSize;

                    QuerySortOrder order = QuerySortOrder.ASC;
                    if (sortOrder == SortOrder.DESCENDING) {
                        order = QuerySortOrder.DESC;
                    }

                    ItemQueryData qData = new ItemQueryData(start, end, sortField, order, filters);
                    itemManager.refreshListData(qData);
                    List<Item> empList = qData.getResult();
                    int count = qData.getTotalResultCount();
                    this.setRowCount(count);
                    currentListOfItems = empList;
                    return empList;
                }

                @Override
                public Object getRowKey(Item object) {
                    if(object == null){
                        return null;
                    }
                    return object.getId();
                }

                @Override
                public Item getRowData(String rowKey) {
                    for (Item item : currentListOfItems) {
                        if (item.getId().toString().equals(rowKey)) {
                            return item;
                        }
                    }

                    return null;
                }

            };
        }
        return lazyModel;
    }



    //GENERATED GETTERS - SETTERS

    public void setLazyModel(LazyDataModel<Item> lazyModel) {
        this.lazyModel = lazyModel;
    }

    public Item getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(Item selectedItem) {
        this.selectedItem = selectedItem;
    }

    public List<Item> getCurrentListOfItems() {
        return currentListOfItems;
    }

    public void setCurrentListOfItems(List<Item> currentListOfItems) {
        this.currentListOfItems = currentListOfItems;
    }

    public List<ItemCategory> getItemCategoryList() {
        return itemCategoryList;
    }

    public void setItemCategoryList(List<ItemCategory> itemCategoryList) {
        this.itemCategoryList = itemCategoryList;
    }
}
