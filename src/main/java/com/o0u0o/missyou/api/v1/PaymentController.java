package com.o0u0o.missyou.api.v1;

import com.o0u0o.missyou.core.interceptors.annotation.ScopeLevel;
import com.o0u0o.missyou.lib.O0u0oWxNotify;
import com.o0u0o.missyou.repository.UserCouponRepository;
import com.o0u0o.missyou.service.WxPaymentNotifyService;
import com.o0u0o.missyou.service.WxPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.net.httpserver.HttpServerImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Positive;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @ClassName PaymentController
 * @Author o0u0o
 * @UpdateUser o0u0o
 * @Date 2021/3/19 5:22 下午
 * @Descripton: 支付接口
 * @Version: v0.0.1
 **/
@RequestMapping("payment")
@RestController
@Validated
public class PaymentController {

    @Autowired
    private WxPaymentService wxPaymentService;

    @Autowired
    private WxPaymentNotifyService wxPaymentNotifyService;

    @Autowired
    private UserCouponRepository userCouponRepository;

    /**
     * 微信支付
     * @param oid 订单id
     */
    @PostMapping("/pay/order/{id}")
    @ScopeLevel
    public Map<String, String> preWxOrder(@PathVariable(name = "id") @Positive Long oid){
        Map<String, String> miniPayParams = this.wxPaymentService.preOrder(oid);
        return miniPayParams;
    }

    /**
     * 回调接口
     * @param request 请求
     * @param response 响应
     * @return
     */
    @RequestMapping("/wx/notify")
    public String payCallback(HttpServletRequest request, HttpServletResponse response){
        InputStream s;
        try {
            s = request.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return O0u0oWxNotify.fail();
        }

        //解析xml的数据
        String xml;
        xml = O0u0oWxNotify.readNotify(s);
        try{
            this.wxPaymentNotifyService.processPayNotify(xml);
        }catch (Exception e){
            return O0u0oWxNotify.fail();
        }

        return O0u0oWxNotify.success();
    }
}
