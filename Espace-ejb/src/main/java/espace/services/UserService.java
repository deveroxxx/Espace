package espace.services;

import espace.entity.Auction;
import espace.entity.User;
import espace.entity.UserRating;
import espace.exceptions.BusinessException;
import espace.managers.AuctionManager;
import espace.managers.UserManager;
import espace.managers.UserRatingManager;
import espace.utils.Log;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
@LocalBean
@Log
public class UserService {


    @Inject
    private UserManager userManager;

    @Inject
    private UserRatingManager userRatingManager;

    @Inject
    private AuctionManager auctionManager;

    public void createUserRating(User user, Long auctionId, Short rating, String text) throws BusinessException {
        Auction auction = auctionManager.select(auctionId);
        if (auction == null) {
            throw new BusinessException("Auction cannot be null!");
        }

        if (rating == null || text == null) {
            throw new BusinessException("Please fill all the fields!");
        }


        if (!auction.getClosed()) {
            throw new BusinessException("You can only rate closed auctions!");
        }

        if (auction.getUserRating() != null) {
            throw new BusinessException("This deal has already been rated!");
        }
        UserRating userRating = new UserRating();
        userRating.setAuction(auction);
        userRating.setRecipient(auction.getOwner());
        userRating.setRating(rating);
        userRating.setSender(user);
        userRating.setText(text);
        userRatingManager.add(userRating);
        auction.setUserRating(userRating);
    }
}
