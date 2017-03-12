package data;

import java.io.IOException;
import java.util.concurrent.Future;

/**
 * Created by ouyangzx on 17/3/4.
 */

public class OrderInfoHunter implements Runnable {

    final Dispatcher mDispatcher;

    final Action mAction;

    final Pay mPay;

    Future<?> future;

    PayInfo mResult;

    public OrderInfoHunter(Dispatcher dispatcher, Action action, Pay pay) {
        mDispatcher = dispatcher;
        mAction = action;
        mPay = pay;
    }

    @Override
    public void run() {
        try {
            mResult = mAction.getRepository().getPayInfo(mAction.getPayId(), mAction.getPrice());
            if (mResult == null) {
                mDispatcher.dispatchFailed(this);
            } else {
                mDispatcher.dispatchComplete(this);
            }
        } catch (IOException e) {
            mDispatcher.dispatchFailed(this);
        }
    }

    public PayInfo getResult() {
        return mResult;
    }

    public Action getAction() {
        return mAction;
    }
}
