package com.osiris.osiris.domains.action;

import com.osiris.osiris.controllers.BaseCRUDController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("action")
public class ActionController extends BaseCRUDController<Action> {

    @Autowired
    public ActionController(ActionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void applyChanges(Action action, Action request) {
        action.setId(request.getId());
    }
}
