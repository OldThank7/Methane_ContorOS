package newland.com.methane;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import cn.com.newland.nle_sdk.responseEntity.SensorInfo;
import cn.com.newland.nle_sdk.responseEntity.base.BaseResponseEntity;
import cn.com.newland.nle_sdk.util.NCallBack;
import cn.com.newland.nle_sdk.util.NetWorkBusiness;

public class ShowMain extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private NetWorkBusiness netWorkBusiness = null;
    private TextView showMethane;
    private Switch aSwitch;
    private ImageView imageView;


    /**
     * 初始化控件
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_main);

        showMethane = findViewById(R.id.ShowMethane);
        imageView = findViewById(R.id.imageView2);


        aSwitch = findViewById(R.id.switchFen);
        aSwitch.setOnCheckedChangeListener(this);


        findViewById(R.id.btn_start).setOnClickListener(this);
    }

    /**
     * 启动一个定时任务
     */
    Timer timer = null;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            netWorkBusiness = new NetWorkBusiness(SettingUtils.getAccessToken(),"http://" + SettingUtils.getIp() + ":" + SettingUtils.getPort() + "/");
            netWorkBusiness.getSensor(SettingUtils.getLoRaGateWayID().toString(), SettingUtils.getMethane(), new NCallBack<BaseResponseEntity<SensorInfo>>(getApplicationContext()) {
                @Override
                protected void onResponse(BaseResponseEntity<SensorInfo> response) {
                    String value = response.getResultObj().getValue();
                    showMethane.setText(value);
                    if (Double.valueOf(value) > SettingUtils.getMethaneMax()){
                        aSwitch.setChecked(true);
                        contor(true);
                    } else {
                        aSwitch.setChecked(false);
                        contor(false);
                    }
                }
            });
        }
    };
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };

    boolean isStart = true;
    @Override
    public void onClick(View view) {
        if (isStart){
            timer = new Timer();
            timer.schedule(timerTask,1000,1000);
            isStart = false;
        }
    }

    /**
     * 在activity即将销毁时，isStart置位true。防止下一次不能初始化Timer
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        isStart = true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuSetting:
                Intent setting = new Intent(this,Setting.class);
                startActivity(setting);
                break;
            case R.id.menuLogout:
                SettingUtils.setAccessToken("");
                netWorkBusiness = null;
                timer.cancel();
                timer = null;
                Intent main = new Intent(this,MainActivity.class);
                startActivity(main);
        }
        return true;
    }


    /**
     * switch控件事件，可以用来控制风扇的启动与停止
     * @param compoundButton
     * @param b
     */
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        int id = compoundButton.getId();

        switch (id){
            case R.id.switchFen:
                contor(b);
                break;
        }
    }

    /**
     * 执行指定标识码的设备
     */
    int rotation = 0;
    private void contor(boolean b){

        netWorkBusiness.control(SettingUtils.getGateWayID().toString(), SettingUtils.getFen(), b, new NCallBack<BaseResponseEntity>(getApplicationContext()) {
            @Override
            protected void onResponse(BaseResponseEntity response) {
            }
        });

        if (b)
            imageView.setImageResource(R.drawable.fen_off);
        else
            imageView.setImageResource(R.drawable.fen_on);
    }
}
