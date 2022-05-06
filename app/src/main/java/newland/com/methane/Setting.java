package newland.com.methane;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cn.com.newland.nle_sdk.util.NetWorkBusiness;

public class Setting extends AppCompatActivity implements View.OnClickListener {

    private EditText IP,PORT;
    private EditText projectID,GateWayID,LoRaGateWayID;
    private EditText fen,methane,methaneMax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        IP = findViewById(R.id.edipnlecloudip);
        PORT = findViewById(R.id.edipnlecloudport);

        projectID = findViewById(R.id.projectID);
        GateWayID = findViewById(R.id.GatewayID);
        LoRaGateWayID = findViewById(R.id.LoRaGatewayID);

        fen = findViewById(R.id.Fen);
        methane = findViewById(R.id.Methane);
        methaneMax = findViewById(R.id.MethaneMax);

        findViewById(R.id.btn_Connection).setOnClickListener(this);
    }

    private Integer EditToInteger(EditText editText){
        String str = editText.getText().toString();
        if (str.equals("") && str == null){
            Toast.makeText(this, "请把配置填写完毕.....", Toast.LENGTH_SHORT).show();
            return null;
        }
        return Integer.valueOf(str);
    }

    private void save(){
        new SettingUtils(
            IP.getText().toString(),
            EditToInteger(PORT),
            EditToInteger(projectID),
            EditToInteger(GateWayID),
            EditToInteger(LoRaGateWayID),
            fen.getText().toString(),
            methane.getText().toString(),
            EditToInteger(methaneMax));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_Connection:
                save();
                break;
        }
    }
}
