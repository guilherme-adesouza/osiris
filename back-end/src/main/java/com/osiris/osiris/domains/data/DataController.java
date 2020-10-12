package com.osiris.osiris.domains.data;

import com.osiris.osiris.controllers.BaseCRUDController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("action")
public class DataController extends BaseCRUDController<Data> {

    @Autowired
    public DataController(DataRepository repository) {
        this.repository = repository;
    }

    @Override
    public void applyChanges(Data data, Data request) {
        data.setId(request.getId());
    }
}