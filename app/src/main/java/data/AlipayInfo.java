package data;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * Created by ouyangzx on 17/3/3.
 */

public class AlipayInfo implements PayInfo {

    /**
     * code : 0
     * orderInfo : {"app_id":"2016080400165436","biz_content":"{\"body\":\"摘要\",\"out_trade_no\":\"8e06f969d1444077950dc1b556c35133\",\"product_code\":\"QUICK_MSECURITY_PAY\",\"seller_id\":\"2088102169916436\",\"subject\":\"订单title\",\"total_amount\":\"0.01\"}","charset":"UTF-8","format":"json","method":"alipay.trade.app.pay","notify_url":"http://15659149069.xicp.net/payBack12.json","sign":"Lq5JUXFhaFJkvf9VWyHh/C+IoTmvTYHJhEHf+V1jH4T1MOmPNrMMqXKdlMc4RREDs0XAo3EBQqKCi3YG4FrM6aWzm5AkidnusL7Z4mRE72uJvhvH8xNeMt7bkt/xNx8JTqNCD9B3J3eRTeLrTv9Y/Mc21X/6TXUX8ANnJZslLfk=","sign_type":"RSA","timestamp":"2017-03-11 11:40:58","version":"1.0"}
     */

    private int code;
    private JSONObject orderInfo;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public <T> T getOrderInfo(Class<T> clazz) {
        if (clazz.isAssignableFrom(Map.class)) {
            return (T) orderInfo;
        }
        return orderInfo.toJavaObject(clazz);
    }

    public JSONObject getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(JSONObject orderInfo) {
        this.orderInfo = orderInfo;
    }

    public static class OrderInfoBean {
        /**
         * app_id : 2016080400165436
         * biz_content : {"body":"摘要","out_trade_no":"8e06f969d1444077950dc1b556c35133","product_code":"QUICK_MSECURITY_PAY","seller_id":"2088102169916436","subject":"订单title","total_amount":"0.01"}
         * charset : UTF-8
         * format : json
         * method : alipay.trade.app.pay
         * notify_url : http://15659149069.xicp.net/payBack12.json
         * sign : Lq5JUXFhaFJkvf9VWyHh/C+IoTmvTYHJhEHf+V1jH4T1MOmPNrMMqXKdlMc4RREDs0XAo3EBQqKCi3YG4FrM6aWzm5AkidnusL7Z4mRE72uJvhvH8xNeMt7bkt/xNx8JTqNCD9B3J3eRTeLrTv9Y/Mc21X/6TXUX8ANnJZslLfk=
         * sign_type : RSA
         * timestamp : 2017-03-11 11:40:58
         * version : 1.0
         */

        private String app_id;
        private String biz_content;
        private String charset;
        private String format;
        private String method;
        private String notify_url;
        private String sign;
        private String sign_type;
        private String timestamp;
        private String version;

        public String getApp_id() {
            return app_id;
        }

        public void setApp_id(String app_id) {
            this.app_id = app_id;
        }

        public String getBiz_content() {
            return biz_content;
        }

        public void setBiz_content(String biz_content) {
            this.biz_content = biz_content;
        }

        public String getCharset() {
            return charset;
        }

        public void setCharset(String charset) {
            this.charset = charset;
        }

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getNotify_url() {
            return notify_url;
        }

        public void setNotify_url(String notify_url) {
            this.notify_url = notify_url;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getSign_type() {
            return sign_type;
        }

        public void setSign_type(String sign_type) {
            this.sign_type = sign_type;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }
}
