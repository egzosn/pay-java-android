package data;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ouyangzx on 17/3/3.
 */

public interface Repository {

    PayInfo getPayInfo(int payId, float price) throws IOException;

}
