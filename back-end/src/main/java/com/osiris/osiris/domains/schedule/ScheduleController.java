package com.osiris.osiris.domains.schedule;

import com.osiris.osiris.controllers.BaseCRUDController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("schedule")
public class ScheduleController extends BaseCRUDController<Schedule> {

    @Autowired
    public ScheduleController(ScheduleRepository repository) {
        this.repository = repository;
    }

    @Override
    public void applyChanges(Schedule schedule, Schedule request) {
        schedule.setId(request.getId());
    }
}
