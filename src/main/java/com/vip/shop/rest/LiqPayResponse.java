package com.vip.shop.rest;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vip.shop.enums.LiqPayResponseStatus;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE
)
public class LiqPayResponse {
    private String err_code;
    private String err_description;
    private LiqPayResponseStatus status;
    private String order_id;
    private String public_key;

    public String getErr_code() {
        return err_code;
    }

    public LiqPayResponse setErr_code(String err_code) {
        this.err_code = err_code;
        return this;
    }

    public String getErr_description() {
        return err_description;
    }

    public LiqPayResponse setErr_description(String err_description) {
        this.err_description = err_description;
        return this;
    }

    public LiqPayResponseStatus getStatus() {
        return status;
    }

    public LiqPayResponse setStatus(LiqPayResponseStatus status) {
        this.status = status;
        return this;
    }

    public String getOrder_id() {
        return order_id;
    }

    public LiqPayResponse setOrder_id(String order_id) {
        this.order_id = order_id;
        return this;
    }

    public String getPublic_key() {
        return public_key;
    }

    public LiqPayResponse setPublic_key(String public_key) {
        this.public_key = public_key;
        return this;
    }
}
