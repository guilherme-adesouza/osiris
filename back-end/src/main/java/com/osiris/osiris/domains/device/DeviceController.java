package com.osiris.osiris.domains.device;

import com.osiris.osiris.controllers.BaseCRUDController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("device")
public class DeviceController extends BaseCRUDController<Device> {

    @Autowired
    public DeviceController(DeviceRepository repository) {
        this.repository = repository;
    }

    @Override
    public void applyChanges(Device device, Device request) {
        device.setDisplayName(request.getDisplayName());
        device.setMacAddress(request.getMacAddress());
        device.setMotorStatus(request.isMotorStatus());
    }
}
