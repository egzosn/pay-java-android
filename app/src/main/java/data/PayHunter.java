package data;

import android.app.Activity;
import android.text.TextUtils;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.Map;
import java.util.concurrent.Future;

/**
 * Created by ouyangzx on 17/3/4.
 */

public class PayHunter implements Runnable {

    final Dispatcher mDispatcher;

    final Action mAction;

    final Pay mPay;

    Future<?> future;

    PayInfo mPayInfo;

    public PayHunter(Dispatcher dispatcher, Action action, Pay pay, PayInfo payInfo) {
        mDispatcher = dispatcher;
        mAction = action;
        mPay = pay;
        mPayInfo = payInfo;
    }


    public Action getAction() {
        return mAction;
    }

    @Override
    public void run() {
        if (mAction.getPayType() == Pay.PayType.APIPAY) {
            PayTask payTask = new PayTask(mPay.getContext());
            String mapToParameters = Utils.getMapToParameters(mPayInfo.getOrderInfo(Map.class));
            Map<String, String> result = payTask.payV2(mapToParameters, false);
            PayResult payResult = new PayResult(result);
            if (TextUtils.equals(payResult.getResultStatus(), "9000")) {
                mDispatcher.dispatchResult(Dispatcher.SUCCESS, this);
            } else {
                mDispatcher.dispatchResult(Dispatcher.FAILED, this);
            }
        } else if (mAction.getPayType() == Pay.PayType.WECHAT) {
            PayReq payReq = mPayInfo.getOrderInfo(PayReq.class);
            IWXAPI msgApi = WXAPIFactory.createWXAPI(mPay.getContext(), null);
            msgApi.registerApp(payReq.appId);
            msgApi.handleIntent(mPay.getContext().getIntent(), new IWXAPIEventHandler() {
                @Override
                public void onReq(BaseReq baseReq) {

                }

                @Override
                public void onResp(BaseResp baseResp) {

                }
            });
        }
    }
}
