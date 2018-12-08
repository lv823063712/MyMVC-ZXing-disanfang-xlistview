package soexample.umeng.com.zhoukaomoni;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText UserName;
    private EditText Password;
    private Button My_Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        UserName = (EditText) findViewById(R.id.UserName);
        Password = (EditText) findViewById(R.id.Password);
        My_Register = (Button) findViewById(R.id.My_Register);

        My_Register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.My_Register:

                break;
        }
    }


}
