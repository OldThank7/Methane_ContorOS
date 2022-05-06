package newland.com.methane;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.com.newland.nle_sdk.requestEntity.SignIn;
import cn.com.newland.nle_sdk.responseEntity.User;
import cn.com.newland.nle_sdk.responseEntity.base.BaseResponseEntity;
import cn.com.newland.nle_sdk.util.NCallBack;
import cn.com.newland.nle_sdk.util.NetWorkBusiness;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editUserName,editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editUserName = findViewById(R.id.editUserName);
        editPassword = findViewById(R.id.editPassowrd);

        findViewById(R.id.btnLogin).setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuSetting:
                Intent setting = new Intent(this,Setting.class);
                startActivity(setting);
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogin:
                NetWorkBusiness netWorkBusiness = new NetWorkBusiness("", "http://" + SettingUtils.getIp() + ":" + SettingUtils.getPort() + "/");
                SignIn signIn = new SignIn(editUserName.getText().toString(),editPassword.getText().toString());
                netWorkBusiness.signIn(signIn, new NCallBack<BaseResponseEntity<User>>(getApplicationContext()) {
                    //回调方法，是否成功都会调用。最好是在次方法中进行简单业务处理
                    @Override
                    protected void onResponse(BaseResponseEntity<User> response) {
                        if (response == null){
                            Toast.makeText(MainActivity.this, "请检查用户密码错误...", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        User resultObj = response.getResultObj();
                        String accessToken = resultObj.getAccessToken();
                        SettingUtils.setAccessToken(accessToken);
                        Toast.makeText(MainActivity.this, "欢迎回来：" + resultObj.getUserID(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this,ShowMain.class);
                        intent.putExtra("accessToken",accessToken);
                        startActivity(intent);
                    }
                });
                break;
        }
    }
}
