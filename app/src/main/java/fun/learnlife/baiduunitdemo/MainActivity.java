package fun.learnlife.baiduunitdemo;

import android.nfc.Tag;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import fun.learnlife.baiduunitdemo.utils.HttpUtil;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "LCY";
    TextView textView;
    private String result="oo)))";
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            textView.setText(result);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tv_result);
        utterance();
        //todo 292002 未找到相应技能。
    }

    private void utterance() {
        // 请求URL
        String talkUrl = "https://aip.baidubce.com/rpc/2.0/unit/bot/chat";
        try {
            // 请求参数
            String params = "{\"bot_session\":\"\",\"log_id\":\"7758521\",\"request\":{\"bernard_level\":1,\"client_session\":\"{\\\"client_results\\\":\\\"\\\", \\\"candidate_options\\\":[]}\",\"query\":\"你好\",\"query_info\":{\"asr_candidates\":[],\"source\":\"KEYBOARD\",\"type\":\"TEXT\"},\"updates\":\"\",\"user_id\":\"88888\"},\"bot_id\":\"1057\",\"version\":\"2.0\"}";
//            String accessToken = "#####调用鉴权接口获取的token#####";
            getUnitResult(talkUrl, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    private void getUnitResult(final String talkUrl, final String params) throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String accessToken = AuthService.getAuth();
                Log.e(TAG, "accessToken="+accessToken);
                try {
                    result = HttpUtil.post(talkUrl, accessToken, "application/json", params);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.e(TAG, "result="+result);
                handler.sendEmptyMessage(1);
            }
        }).start();
    }
}
