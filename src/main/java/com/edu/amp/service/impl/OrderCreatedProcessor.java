package com.edu.amp.service.impl;

import com.edu.amp.anno.OrderProcessor;
import com.edu.amp.check.Checkable;
import com.edu.amp.check.Checker;
import com.edu.amp.check.impl.CreateParamChecker;
import com.edu.amp.check.impl.UnfinshChecker;
import com.edu.amp.check.impl.UserChecker;
import com.edu.amp.constant.OrderEventEnum;
import com.edu.amp.constant.OrderStateEnum;
import com.edu.amp.constant.ServiceType;
import com.edu.amp.context.CreateOrderContext;
import com.edu.amp.context.StateContext;
import com.edu.amp.domain.OrderInfo;
import com.edu.amp.domain.ServiceResult;
import com.edu.amp.event.CreateEvent;
import com.edu.amp.service.AbstractStateProcessor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author apple
 */
@Slf4j
@OrderProcessor(state = OrderStateEnum.INIT, event = OrderEventEnum.CREATE, bizCode = "BUSINESS")
public class OrderCreatedProcessor extends AbstractStateProcessor<String, CreateOrderContext> {
    @Resource
    private CreateParamChecker createParamChecker;
    @Resource
    private UserChecker userChecker;
    @Resource
    private UnfinshChecker unfinshChecker;
    @Override
    public Checkable getCheckable(StateContext<CreateOrderContext> context) {
        return new Checkable() {
            @Override
            public List<Checker> getParamChecker() {
                return Arrays.asList(createParamChecker);
            }
            @Override
            public List<Checker> getSyncChecker() {
                return Collections.EMPTY_LIST;
            }
            @Override
            public List<Checker> getAsyncChecker() {
                return Arrays.asList(userChecker, unfinshChecker);
            }
        };
    }

    @Override
    public ServiceResult<String> check(StateContext<CreateOrderContext> context) {
        return null;
    }

    @Override
    public String getNextState(StateContext<CreateOrderContext> context) {
        /*if (OrderStateEnum.INIT.equalsIgnoreCase(context.getOrderStateEvent().getEventType())){
            return OrderStateEnum.INIT;
        }*/
        return OrderStateEnum.NEW;
    }

    @Override
    public ServiceResult<String> action(String nextState, StateContext<CreateOrderContext> context) throws Exception {
        CreateEvent createEvent = (CreateEvent) context.getOrderStateEvent();
        // 促销信息信息
        String promtionInfo = this.doPromotion();
        return null;
    }

    @Override
    public ServiceResult<String> save(String nextState, StateContext<CreateOrderContext> context) throws Exception {
        OrderInfo orderInfo = context.getContext().getOrderInfo();
        // 更新状态
        orderInfo.setOrderState(nextState);
        // 持久化
        // this.updateOrderInfo(orderInfo);
        log.info("save BUSINESS order success, userId:{}, orderId:{}", orderInfo.getUserId(), orderInfo.getOrderId());
        return new ServiceResult<>(orderInfo.getOrderId(), "business下单成功");

    }

    @Override
    public void after(StateContext<CreateOrderContext> context) {

    }

    /**
     * 促销相关扩展点
     */
    protected String doPromotion() {
        return "1";
    }

    @Override
    public boolean filter(StateContext<?> context) {
        OrderInfo orderInfo = (OrderInfo) context.getFsmOrder();
        if (orderInfo.getServiceType() == ServiceType.TAKEOFF_CAR) {
            return true;
        }
        return false;
    }
}
