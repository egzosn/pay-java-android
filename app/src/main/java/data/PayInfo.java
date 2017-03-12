package data;

import java.util.Map;

/**
 * Created by ouyangzx on 17/3/3.
 */

public interface PayInfo {
    <T>T getOrderInfo(Class<T> clazz);
}
