package data;

/**
 * Created by ouyangzx on 17/3/4.
 */

public class Action {
    private int payId;
    private float price;

    private Pay.PayType mPayType;
    private Pay mPay;

    private Repository mRepository;


    public Action(int payId, float price, Pay.PayType payType, Pay pay, Repository repository) {
        this.payId = payId;
        this.price = price;
        mPayType = payType;
        mPay = pay;
        mRepository = repository;
    }

    public Pay.PayType getPayType() {
        return mPayType;
    }

    public int getPayId() {
        return payId;
    }

    public void setPayId(int payId) {
        this.payId = payId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Pay getPay() {
        return mPay;
    }

    public Repository getRepository() {
        return mRepository;
    }


}
