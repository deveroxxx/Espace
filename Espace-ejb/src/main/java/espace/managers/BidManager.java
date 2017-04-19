package espace.managers;

import espace.entity.Bid;
import espace.template.TemplateManager;
import espace.utils.Log;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
@Log
public class BidManager extends TemplateManager<Bid> {






    @Override
    protected Class getMyClass() {
        return Bid.class;
    }
}
