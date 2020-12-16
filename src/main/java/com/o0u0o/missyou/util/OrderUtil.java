//package com.o0u0o.missyou.util;
//
//import lombok.Value;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//import java.util.Calendar;
//
///**
// * 订单相关工具类
// * @author mac
// * @date 2020/12/16 3:37 下午
// */
//@Component
//public class OrderUtil {
//    // B3230651812529
//    private static String[] yearCodes;
//
//    @Value("${missyou.year-codes}")
//    public void setYearCodes(String yearCodes) {
//        String[] chars = yearCodes.split(",");
//        OrderUtil.yearCodes = chars;
//    }
//
//    public static String makeOrderNo() {
//        StringBuffer joiner = new StringBuffer();
//        Calendar calendar = Calendar.getInstance();
//        String mills = String.valueOf(calendar.getTimeInMillis());
//        String micro = LocalDateTime.now().toString();
//        String random = String.valueOf(Math.random()*1000).substring(0,2);
//        joiner.append(OrderUtil.yearCodes[calendar.get(Calendar.YEAR) - 2019])
//                .append(Integer.toHexString(calendar.get(Calendar.MONTH)+1).toUpperCase())
//                .append(calendar.get(Calendar.DAY_OF_MONTH))
//                .append(mills.substring(mills.length()-5, mills.length()))
//                .append(micro.substring(micro.length()-3, micro.length()))
//                .append(random);
//        return joiner.toString();
//
//    }
//}
