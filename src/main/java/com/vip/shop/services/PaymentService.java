package com.vip.shop.services;

import com.vip.shop.dto.OrderShortDto;

public interface PaymentService {

    String preparePaymentButton(OrderShortDto order);
}
