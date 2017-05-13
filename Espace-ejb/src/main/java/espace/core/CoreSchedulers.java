package espace.core;

import espace.services.AuctionService;
import espace.utils.Log;
import org.apache.log4j.Logger;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
@Log
public class CoreSchedulers {

    private static Logger logger = Logger.getLogger(CoreSchedulers.class);

    @Inject
    private AuctionService auctionService;

    @Schedule(hour = "*", minute = "*", second = "0", persistent = false)
    public void doAuctionEndCheck() {
        try {
            auctionService.closeExpiredAuctions();
        } catch (Throwable th) {
            logger.error("Error happend during doAuctionEndCheck scheduled Task", th);
        }

    }




}
