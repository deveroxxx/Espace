package espace.managers;

import espace.template.TemplateManager;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class BidManager extends TemplateManager {






    @Override
    protected Class getMyClass() {
        return this.getClass();
    }
}
