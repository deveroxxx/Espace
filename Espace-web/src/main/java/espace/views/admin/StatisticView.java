package espace.views.admin;

import espace.services.StatisticService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import java.io.Serializable;

@ManagedBean(name = "statisticView" )
@RequestScoped
public class StatisticView implements Serializable {

    @Inject
    private StatisticService statisticService;

    int activeAuctions;
    int closedAuctionsWithWinner;
    int closedAuctionsWithoutWinner;
    int userCount;

    @PostConstruct
    public void init() {
        activeAuctions = statisticService.countActiveAuctions();
        closedAuctionsWithWinner = statisticService.countClosedAuctionsWithWinner();
        closedAuctionsWithoutWinner = statisticService.countClosedAuctionsWithoutWinner();
        userCount = statisticService.countOfRegisteredUsers();
    }


    public int getActiveAuctions() {
        return activeAuctions;
    }

    public int getClosedAuctionsWithWinner() {
        return closedAuctionsWithWinner;
    }

    public int getClosedAuctionsWithoutWinner() {
        return closedAuctionsWithoutWinner;
    }

    public int getUserCount() {
        return userCount;
    }

}
