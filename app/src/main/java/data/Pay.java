package data;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.squareup.picasso.Picasso;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by ouyangzx on 17/3/3.
 */

public class Pay {

    final Activity mContext;
    final PayType mPayType;
    final Dispatcher mDispatcher;

    Listener mListener;
    final Repository mRepository;


    public Pay(Activity context, PayType payType, Dispatcher dispatcher, Repository repository) {
        mContext = context;
        mPayType = payType;
        mDispatcher = dispatcher;
        mRepository = repository;
    }

    public void into(int payId, float price, Listener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Listener must not be null.");
        }
        mListener = listener;
        submit(new Action(payId, price, mPayType, this, mRepository));
    }

    private void submit(Action action) {
        mDispatcher.submit(action);
    }

    public interface Listener {

        void paySuccess();

        void payError();
    }


    public enum PayType {
        APIPAY, WECHAT;
    }

    public static class Builder {
        private final Activity mContext;
        private PayType mPayType;
        private Dispatcher mDispatcher;

        private ExecutorService mExecutorService;
        private Repository mRepository;

        public Builder(Activity context, PayType payType, Repository repository) {

            if (context == null) {
                throw new IllegalArgumentException("Context must not be null.");
            }

            mPayType = payType;
            mRepository = repository;
            mContext = context;
        }

        public Builder executor(ExecutorService executorService) {
            if (executorService == null) {
                throw new IllegalArgumentException("Executor service must not be null.");
            }
            if (this.mExecutorService != null) {
                throw new IllegalStateException("Executor service already set.");
            }
            this.mExecutorService = executorService;
            return this;
        }


        public Builder payType(PayType payType) {
            this.mPayType = payType;
            return this;
        }


        public Pay build() {
            if (mExecutorService == null) {
                mExecutorService = Executors.newCachedThreadPool();
            }

            if (mDispatcher == null) {
                mDispatcher = new Dispatcher(mContext, mPayType, mExecutorService, HANDLER, mRepository);
            }


            return new Pay(mContext, mPayType, mDispatcher, mRepository);
        }

    }

    static final Handler HANDLER = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            PayHunter payHunter = (PayHunter) msg.obj;
            Pay pay = payHunter.getAction().getPay();
            if (msg.what == Dispatcher.SUCCESS) {
                if (pay.mListener != null) {
                    pay.mListener.paySuccess();
                }
            } else {
                if (pay.mListener != null) {
                    pay.mListener.payError();
                }

            }
        }
    };

    public Activity getContext() {
        return mContext;
    }
}
