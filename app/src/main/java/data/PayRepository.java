package data;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.EnvUtils;

import java.io.IOException;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ouyangzx on 17/3/3.
 */

public class PayRepository implements Repository {

    private String mURL;


    private OkHttpClient mOkHttpClient;

    public PayRepository(String url) {
        mOkHttpClient = new OkHttpClient();
        mURL = url;
    }


    @Override
    public PayInfo getPayInfo(int payId, float price) throws IOException {
        //Request request = new Request.Builder().url("http://15659149069.xicp.net/getOrderInfo?payId=+" + payId + "+&transactionType=APP&price=" + price).build();
        RequestBody requestBody = new MultipartBody.Builder()
                .addFormDataPart("payId", String.valueOf(payId))
                .addFormDataPart("price", String.valueOf(price))
                .addFormDataPart("transactionType", "APP")
                .build();
        mURL = mURL + "?payId=" + payId + "&transactionType=APP&price=" + price;
        Request request = new Request.Builder().url(mURL).build();
        Response response = mOkHttpClient.newCall(request).execute();
        String str = response.body().string();
        AlipayInfo jsonObject = JSON.parseObject(str, AlipayInfo.class);
        return jsonObject;
    }
}
