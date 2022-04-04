package com.edu.amp.service.impl;

import com.edu.amp.anno.OrderProcessor;
import com.edu.amp.constant.OrderEventEnum;
import com.edu.amp.constant.OrderStateEnum;

/**
 * @author apple
 */
@OrderProcessor(state = OrderStateEnum.INIT, event = OrderEventEnum.CREATE, bizCode = "TAXI")
public class OrderCreatedProcessor4Taxi extends OrderCreatedProcessor {
    @Override
    protected String doPromotion() {
        return "taxt1";
    }
}
