package com.edu.amp.service.impl;

import com.edu.amp.anno.OrderProcessor;
import com.edu.amp.check.Checkable;
import com.edu.amp.context.CreateOrderContext;
import com.edu.amp.context.StateContext;
import com.edu.amp.domain.ServiceResult;
import com.edu.amp.service.AbstractStateProcessor;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Service;

/**
 * @author apple
 */
@Service
@OrderProcessor(state = "INIT", event = "FINISH", bizCode = "BUSINESS", sceneId = "H5")
public class StateFinishProcessor extends AbstractStateProcessor<String, CreateOrderContext> {
    @Override
    public ServiceResult<String> check(StateContext<CreateOrderContext> context) {
        return null;
    }

    @Override
    public String getNextState(StateContext<CreateOrderContext> context) {
        return null;
    }

    @Override
    public ServiceResult<String> action(String nextState, StateContext<CreateOrderContext> context) throws Exception {
        return null;
    }

    @Override
    public ServiceResult<String> save(String nextState, StateContext<CreateOrderContext> context) throws Exception {
        return null;
    }

    @Override
    public void after(StateContext<CreateOrderContext> context) {

    }

    @Override
    public Checkable getCheckable(StateContext<CreateOrderContext> context) {
        return null;
    }

    @Override
    public void prepare(StateContext<CreateOrderContext> context) {
        super.prepare(context);
    }

    @Override
    public boolean filter(StateContext<?> context) {
        return false;
    }
}
