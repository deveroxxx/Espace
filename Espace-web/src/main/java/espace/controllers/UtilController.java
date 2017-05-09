package espace.controllers;

import espace.services.DatabaseInicializer;

import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import java.io.Serializable;

@ManagedBean(name = "utilController", eager = true)
@ViewScoped
public class UtilController implements Serializable {

    @Inject
    DatabaseInicializer databaseInicializer;


    public String createAdminUser() {
        databaseInicializer.createAdminUser();
        return null;
    }

    public String createUsers() {
        databaseInicializer.createUsers();
        return null;
    }

    public String createCategories() {
        databaseInicializer.createCategories();
        return null;
    }

    public String createItems() {
        databaseInicializer.createItems();
        return null;
    }

    public String createDumyDatatas() {
        databaseInicializer.createDumyDatas();
        return null;
    }


}
