package com.edu.amp.service.impl;

import com.edu.amp.anno.OrderProcessor;
import com.edu.amp.check.Checkable;
import com.edu.amp.check.Checker;
import com.edu.amp.check.impl.CreateParamChecker;
import com.edu.amp.check.impl.UnfinshChecker;
import com.edu.amp.check.impl.UserChecker;
import com.edu.amp.context.CreateOrderContext;
import com.edu.amp.context.StateContext;
import com.edu.amp.domain.ServiceResult;
import com.edu.amp.service.AbstractStateProcessor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 创建订单状态对应的状态处理器
 * @author apple
 */
@Service
@OrderProcessor(state = "INIT", event = "CREATE", bizCode = "BUSINESS", sceneId = "H5")
public class StateCreateProcessor extends AbstractStateProcessor<String, CreateOrderContext> {
    @Resource
    private CreateParamChecker createParamChecker;
    @Resource
    private UserChecker userChecker;
    @Resource
    private UnfinshChecker unfinshChecker;

    @Override
    public void prepare(StateContext<CreateOrderContext> context) {
        System.out.println("StateCreateProcessor prepare method");
    }

    @Override
    public ServiceResult<String> check(StateContext<CreateOrderContext> context) {
        return null;
    }

    @Override
    public String getNextState(StateContext<CreateOrderContext> context) {
        return "NEW";
    }

    @Override
    public ServiceResult<String> action(String nextState, StateContext<CreateOrderContext> context) throws Exception {
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setSuccess(true);
        return serviceResult;
    }

    @Override
    public ServiceResult<String> save(String nextState, StateContext<CreateOrderContext> context) throws Exception {
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setSuccess(true);
        return serviceResult;
    }

    @Override
    public void after(StateContext<CreateOrderContext> context) {

    }

    @Override
    public Checkable getCheckable(StateContext<CreateOrderContext> context) {
        return new Checkable() {
            @Override
            public List<Checker> getParamChecker() {
                return Arrays.asList(createParamChecker);
            }
            @Override
            public List<Checker> getSyncChecker() {
                return Arrays.asList(createParamChecker);
            }
            @Override
            public List<Checker> getAsyncChecker() {
                return Arrays.asList(userChecker, unfinshChecker);
            }
        };
    }

    @Override
    public boolean filter(StateContext<?> context) {
        return true;
    }
}