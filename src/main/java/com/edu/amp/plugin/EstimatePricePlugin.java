package com.edu.amp.plugin;

import com.edu.amp.anno.ProcessorPlugin;
import com.edu.amp.constant.OrderEventEnum;
import com.edu.amp.constant.OrderStateEnum;
import com.edu.amp.context.CreateOrderContext;
import com.edu.amp.context.StateContext;
import com.edu.amp.domain.ServiceResult;

/**
 * 预估价插件
 * @author apple
 */
@ProcessorPlugin(state = OrderStateEnum.INIT, event = OrderEventEnum.CREATE, bizCode = "BUSINESS")
public class EstimatePricePlugin implements PluginHandler<String, CreateOrderContext> {
    @Override
    public ServiceResult<String> action(StateContext<CreateOrderContext> context) throws Exception {
        //  String price = priceSerive.getPrice();
        String price = "";
        context.getContext().setEstimatePriceInfo(price);
        return new ServiceResult();
    }
}