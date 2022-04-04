package com.edu.amp.fsm.impl;

import com.edu.amp.constant.OrderStateEnum;
import com.edu.amp.domain.OrderInfo;
import com.edu.amp.fsm.FsmOrderService;
import com.edu.amp.service.FsmOrder;
import org.springframework.stereotype.Service;
/**
 * @author apple
 */
@Service
public class FsmOrderServiceImpl implements FsmOrderService {
    @Override
    public FsmOrder getFsmOrder(String orderId) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderId(orderId);
        orderInfo.setOrderState(OrderStateEnum.INIT);
        orderInfo.setBizCode("BUSINESS");
        orderInfo.setSceneId("H5");
        return orderInfo;
    }
}
