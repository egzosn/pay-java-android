package data;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import java.util.Map;
import java.util.concurrent.ExecutorService;

import static data.Pay.*;

/**
 * Created by ouyangzx on 17/3/3.
 */

public class Dispatcher {

    private static final String DISPATCHER_THREAD_NAME = "Dispatcher";

    static final int REQUEST_SUBMIT = 1;


    static final int SUCCESS = 10;
    static final int FAILED = 11;

    static final int REQUEST_FAILED_ALIPAY = 2;
    static final int REQUEST_FAILED_WXPAY = 3;
    static final int REQUEST_PAY = 4;
    final DispatcherThread dispatcherThread;
    final Handler mHandler;
    final Handler mMainThreadHandler;
    final Context mContext;
    final ExecutorService mExecutorService;
    final Repository mRepository;
    final PayType mPayType;

    Dispatcher(Context context, PayType payType, ExecutorService service, Handler mainThreadHandler, Repository repository) {
        dispatcherThread = new DispatcherThread();
        dispatcherThread.start();

        Utils.flushStackLocalLeaks(dispatcherThread.getLooper());
        mContext = context;
        mExecutorService = service;
        mPayType = payType;
        mHandler = new DispatcherHandler(dispatcherThread.getLooper(), this);
        mRepository = repository;
        mMainThreadHandler = mainThreadHandler;

    }


    void submit(Action action) {
        mHandler.sendMessage(mHandler.obtainMessage(REQUEST_SUBMIT, action));

    }

    void dispatchComplete(OrderInfoHunter hunter) {
        dispatchPerformPay(hunter);
    }

    void dispatchPerformPay(OrderInfoHunter hunter) {
        mHandler.sendMessage(mHandler.obtainMessage(REQUEST_PAY, hunter));
    }


    void dispatchFailed(OrderInfoHunter hunter) {

    }

    void performSubmit(Action action) {
        OrderInfoHunter hunter = new OrderInfoHunter(this, action, action.getPay());
        hunter.future = mExecutorService.submit(hunter);
    }

    public void dispatchResult(int code, PayHunter hunter) {
        Message m = new Message();
        m.what = code;
        m.obj = hunter;
        mMainThreadHandler.sendMessage(m);
    }

    void performPay(OrderInfoHunter hunter) {
        PayInfo payInfo = hunter.getResult();
        PayHunter payHunter = new PayHunter(this, hunter.getAction(), hunter.getAction().getPay(), payInfo);
        payHunter.future = mExecutorService.submit(payHunter);
    }

    private static class DispatcherHandler extends Handler {
        private final Dispatcher mDispatcher;

        public DispatcherHandler(Looper looper, Dispatcher dispatcher) {
            super(looper);
            mDispatcher = dispatcher;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REQUEST_SUBMIT:
                    Action action = (Action) msg.obj;
                    mDispatcher.performSubmit(action);
                    break;
                case REQUEST_PAY:
                    OrderInfoHunter hunter = (OrderInfoHunter) msg.obj;
                    mDispatcher.performPay(hunter);
                    break;

            }
        }
    }


    static class DispatcherThread extends HandlerThread {

        public DispatcherThread() {
            super(DISPATCHER_THREAD_NAME, 10);
        }
    }

}
