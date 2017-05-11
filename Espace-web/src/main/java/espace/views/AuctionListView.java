package espace.views;

import espace.data.AuctionData;
import espace.data.AuctionFilters;
import espace.data.QueryData;
import espace.entity.Auction;
import espace.entity.Item;
import espace.entity.ItemCategory;
import espace.enums.AuctionSortField;
import espace.enums.QuerySortOrder;
import espace.managers.AuctionManager;
import espace.managers.ItemCategoryManager;
import espace.managers.ItemManager;
import espace.managers.UserManager;
import espace.utils.Log;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

@ManagedBean(name = "auctionListView")
@SessionScoped
@Log
public class AuctionListView implements Serializable {

    @Inject
    private UserManager userManager;

    @Inject
    private ItemManager itemManager;

    @Inject
    private ItemCategoryManager itemCategoryManager;

    @Inject
    private AuctionManager auctionManager;


    private List<Auction> resultList;

    private List<Auction> currentListOfAuctions;

    private List<ItemCategory> selectedCategories;
    private List<ItemCategory> categories;


    private LazyDataModel<Auction> lazyModel;
    private Item selectedItem;

    private List<Item> currentListOfItems;


    //page info
    int start;
    int end;

    private AuctionFilters filters;

    //order
    private LinkedHashMap<AuctionSortField, String> orderDropdown;
    private AuctionSortField sortOrder;



    @PostConstruct
    public void init() {
        //default list
        resultList = new ArrayList<Auction>();
        //order dropdown dropdown init
        orderDropdown = new LinkedHashMap<>();
        orderDropdown.put(AuctionSortField.PRICE_ASC, "Price Asc");
        orderDropdown.put(AuctionSortField.PRICE_DESC, "Price Desc");
        orderDropdown.put(AuctionSortField.CLOSE_DATE_ASC, "Close date Asc");
        orderDropdown.put(AuctionSortField.CLOSE_DATE_DESC, "Close date Desc");
        orderDropdown.put(AuctionSortField.NAME_ASC, "Auction title asc");
        orderDropdown.put(AuctionSortField.NAME_DESC, "Auction title desc");
        orderDropdown.put(AuctionSortField.NEWEST_FIRST, "Newest first");
        //default order
        sortOrder = AuctionSortField.NEWEST_FIRST;
        //default filters
        filters = new AuctionFilters();
        //category dropdown
        selectedCategories = new ArrayList<>();
        categories = itemCategoryManager.listAll();
    }


    public void applyFilters() {
         getLazyModel();
    }


    public LazyDataModel<Auction> getLazyModel() {
        if (lazyModel == null) {
            lazyModel = new LazyDataModel<Auction>() {
            @Override
            public List<Auction> load(int first, int pageSize, String sortField, SortOrder nonused_sortOrder, Map<String, Object> nonused_filters) {
                //log.info("first=" + first + ", pagesize=" + pageSize + ", sortfield=" + sortField + " sortorder=" + sortOrder + " filter:" + filters);
                start = first;
                end = first + pageSize;
                filters.setCategories(selectedCategories);
                AuctionData data  = auctionManager.refreshListData(filters, sortOrder, start, end);
                List<Auction> resultList = data.getResult();
                long count = data.getCount();
                this.setRowCount(Math.toIntExact(count));
                currentListOfAuctions = resultList;
                return resultList;
            }
            };
        }
        return lazyModel;
    }

    //TODO: getterből nem kell ennyi

    public AuctionSortField getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(AuctionSortField sortOrder) {
        this.sortOrder = sortOrder;
    }

    public LinkedHashMap<AuctionSortField, String> getOrderDropdown() {
        return orderDropdown;
    }

    public void setOrderDropdown(LinkedHashMap<AuctionSortField, String> orderDropdown) {
        this.orderDropdown = orderDropdown;
    }

    public AuctionFilters getFilters() {
        return filters;
    }

    public void setFilters(AuctionFilters filters) {
        this.filters = filters;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public List<Item> getCurrentListOfItems() {
        return currentListOfItems;
    }

    public void setCurrentListOfItems(List<Item> currentListOfItems) {
        this.currentListOfItems = currentListOfItems;
    }

    public Item getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(Item selectedItem) {
        this.selectedItem = selectedItem;
    }

    public void setLazyModel(LazyDataModel<Auction> lazyModel) {
        this.lazyModel = lazyModel;
    }

    public List<Auction> getCurrentListOfAuctions() {
        return currentListOfAuctions;
    }

    public void setCurrentListOfAuctions(List<Auction> currentListOfAuctions) {
        this.currentListOfAuctions = currentListOfAuctions;
    }

    public List<Auction> getResultList() {
        return resultList;
    }

    public void setResultList(List<Auction> resultList) {
        this.resultList = resultList;
    }

    public List<ItemCategory> getSelectedCategories() {
        return selectedCategories;
    }

    public void setSelectedCategories(List<ItemCategory> selectedCategories) {
        this.selectedCategories = selectedCategories;
    }

    public List<ItemCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<ItemCategory> categories) {
        this.categories = categories;
    }
}
