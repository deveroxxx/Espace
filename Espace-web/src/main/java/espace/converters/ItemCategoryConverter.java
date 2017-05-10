package espace.converters;

import espace.entity.ItemCategory;
import espace.managers.ItemCategoryManager;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@ApplicationScoped
@ManagedBean(name = "itemCategoryConverter")
public class ItemCategoryConverter implements Converter {

    @Inject
    ItemCategoryManager itemCategoryManager;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            return itemCategoryManager.getByName(value);
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            return ((ItemCategory) object).getName();
        } else {
            return null;
        }
    }
}
