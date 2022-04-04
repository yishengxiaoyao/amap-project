package com.edu.amp.check.impl;

import com.edu.amp.check.Checker;
import com.edu.amp.context.StateContext;
import com.edu.amp.domain.ServiceResult;
import org.springframework.stereotype.Service;

/**
 * @author apple
 */
@Service
public class UserChecker implements Checker<Boolean,String> {
    @Override
    public ServiceResult<Boolean> check(StateContext<String> context) {
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setSuccess(true);
        return serviceResult;
    }

    @Override
    public int order() {
        return Checker.super.order();
    }
}
