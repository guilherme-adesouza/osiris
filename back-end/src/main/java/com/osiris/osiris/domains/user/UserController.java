package com.osiris.osiris.domains.user;

import com.osiris.osiris.controllers.BaseCRUDController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController extends BaseCRUDController<User> {

    @Autowired
    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void applyChanges(User user, User request) {
        user.setLogin(request.getLogin());
        user.setPassword(request.getPassword());
        user.setMobileId(request.getMobileId());
    }
}
