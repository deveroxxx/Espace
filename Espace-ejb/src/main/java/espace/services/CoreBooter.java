package espace.services;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
@Startup
public class CoreBooter implements Serializable {

    private static final Logger logger = Logger.getLogger(CoreBooter.class.getName());

    @Inject
    DatabaseInicializer databaseInicializer;

    @PostConstruct
    public void loader() {
        logger.log(Level.INFO, "CoreBooter started... ");
     //   databaseInicializer.createAdminUser();
     //   databaseInicializer.createUsers();
     //   databaseInicializer.createCategories();
     //   databaseInicializer.createItems();
        logger.log(Level.INFO, "CoreBooter started... ");
    }

}
