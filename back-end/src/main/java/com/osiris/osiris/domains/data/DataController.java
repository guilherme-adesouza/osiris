package com.osiris.osiris.domains.data;

import com.osiris.osiris.controllers.BaseCRUDController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("data")
public class DataController extends BaseCRUDController<Data> {

    @Autowired
    public DataController(DataRepository repository) {
        this.repository = repository;
    }

    @Override
    public void applyChanges(Data data, Data request) {
        data.setDevice(request.getDevice());
        data.setHumidity(request.getHumidity());
        data.setLuminosity(request.getLuminosity());
        data.setSoil(request.getSoil());
        data.setTemperature(request.getTemperature());
        data.setWaterHigh(request.isWaterHigh());
        data.setWaterLow(request.isWaterLow());
        data.setMotorStatus(request.getMotorStatus());
    }
}
