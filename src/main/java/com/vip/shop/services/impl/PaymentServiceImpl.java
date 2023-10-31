package com.vip.shop.services.impl;

import com.liqpay.LiqPay;
import com.sun.istack.FinalArrayList;
import com.vip.shop.dto.OrderShortDto;
import com.vip.shop.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@PropertySource("/payment.properties")
public class PaymentServiceImpl implements PaymentService {

    private final String PUBLIC_KEY;
    private final String PRIVATE_KEY;
    private final String SERVER_URL;
    private final String RESULT_URL;

    private final Environment environment;

    @Autowired
    public PaymentServiceImpl(Environment environment) {
        this.environment = environment;

        this.PUBLIC_KEY = environment.getProperty("liqpay.api.public.key");
        this.PRIVATE_KEY = environment.getProperty("liqpay.api.private.key");
        this.RESULT_URL = environment.getProperty("liqpay.api.result_url");
        this.SERVER_URL = environment.getProperty("liqpay.api.server_url");
    }

    @Override
    public String preparePaymentButton(OrderShortDto order) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("action", "pay");
        params.put("amount", String.valueOf(order.getPrice()));
        params.put("currency", "USD");
        params.put("description", "");
        params.put("order_id", String.valueOf(order.getId()));
        params.put("version", "3");
        params.put("result_url", RESULT_URL);
        params.put("server_url", SERVER_URL);
        params.put("language", "en");
        LiqPay liqpay = new LiqPay(PUBLIC_KEY, PRIVATE_KEY);
        return liqpay.cnb_form(params);
    }
}
