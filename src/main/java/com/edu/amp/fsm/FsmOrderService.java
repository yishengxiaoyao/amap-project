package com.edu.amp.fsm;

import com.edu.amp.service.FsmOrder;

/**
 * @author apple
 */
public interface FsmOrderService {
    /**
     * 通过订单id获取订单信息
     * @param orderId
     * @return
     */
    FsmOrder getFsmOrder(String orderId);
}
