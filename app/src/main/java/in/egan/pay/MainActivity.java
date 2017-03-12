package in.egan.pay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.EnvUtils;

import data.PayRepository;
import data.Pay;

public class MainActivity extends AppCompatActivity {

    private EditText mEtId;
    private EditText mEtPrice;
    private EditText mEtURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEtId = (EditText) findViewById(R.id.et_id);
        mEtPrice = (EditText) findViewById(R.id.et_price);
        mEtURL = (EditText) findViewById(R.id.et_url);


        findViewById(R.id.btn_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX); //开启沙箱

                if (TextUtils.isEmpty(mEtId.getText()) || TextUtils.isEmpty(mEtPrice.getText()) || TextUtils.isEmpty(mEtURL.getText())) {
                    Toast.makeText(MainActivity.this, "请将数据填写完整", Toast.LENGTH_LONG);
                    return;
                }


                Pay pay = new Pay.Builder(MainActivity.this, Pay.PayType.APIPAY, new PayRepository(mEtURL.getText().toString())).build();
                pay.into(Integer.parseInt(mEtId.getText().toString()), Float.parseFloat(mEtPrice.getText().toString()), new Pay.Listener() {
                    @Override
                    public void paySuccess() {
                        Toast.makeText(MainActivity.this, "支付成功", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void payError() {
                        Toast.makeText(MainActivity.this, "支付失败", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


    }
}
