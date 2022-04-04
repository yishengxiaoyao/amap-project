package com.edu.amp.controller;

import com.edu.amp.event.CreateEvent;
import com.edu.amp.fsm.OrderFsmEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author apple
 */
@RestController
public class AmapController {

    @Autowired
    private OrderFsmEngine orderFsmEngine;

    @GetMapping("/test")
    public String getData() {
        CreateEvent createEvent = new CreateEvent("CREATE", "1", "1", "INIT");
        try {
            orderFsmEngine.sendEvent(createEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "message";
    }
}
