package data;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * Created by ouyangzx on 17/3/3.
 */

public class Utils {

    static final int THREAD_LEAK_CLEANING_MS = 1000;


    static void flushStackLocalLeaks(Looper looper) {
        Handler handler = new Handler(looper) {
            @Override public void handleMessage(Message msg) {
                sendMessageDelayed(obtainMessage(), THREAD_LEAK_CLEANING_MS);
            }
        };
        handler.sendMessageDelayed(handler.obtainMessage(), THREAD_LEAK_CLEANING_MS);
    }


    /**
     * Map转化为对应得参数字符串
     * @param pe
     * @return
     */
    /**
     * Map转化为对应得参数字符串
     * @param pe
     * @return
     */
    public static String getMapToParameters(Map pe){
        StringBuilder builder = new StringBuilder();
        for (Object key : pe.keySet()) {
            Object o = pe.get(key);

            if (null == o) {
                continue;
            }

            if (o instanceof List) {
                o = ((List) o).toArray();
            }
            try {
                if (o instanceof Object[]) {
                    Object[] os = (Object[]) o;
                    String valueStr = "";
                    for (int i = 0, len = os.length; i < len; i++) {
                        if (null == os[i]) {
                            continue;
                        }
                        String value = os[i].toString().trim();
                        valueStr += (i == len - 1) ?  value :  value + ",";
                    }
                    builder.append(key).append("=").append(URLEncoder.encode(valueStr, "utf-8")).append("&");

                    continue;
                }
                builder.append(key).append("=").append(URLEncoder.encode((String) pe.get(key), "utf-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        if (builder.length() > 1) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }

}
