package espace.data;

import espace.entity.Auction;

import java.util.ArrayList;
import java.util.List;

public class AuctionData {

    private List<Auction> result;
    private long totalCount;
    private long count;

    public AuctionData() {
        result = new ArrayList<>();
    }

    public List<Auction> getResult() {
        return result;
    }

    public void setResult(List<Auction> result) {
        this.result = result;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
