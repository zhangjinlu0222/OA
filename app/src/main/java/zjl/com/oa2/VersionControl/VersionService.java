package zjl.com.oa2.VersionControl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import com.alibaba.fastjson.JSON;
import com.allenliu.versionchecklib.core.AVersionService;

import zjl.com.oa2.ApplicationConfig.Constant;

import static com.alibaba.fastjson.JSON.parseObject;

public class VersionService extends AVersionService {
    private Context context;
    public VersionService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onResponses(AVersionService service, String response) {
        context = getApplicationContext();
        if (parseObject(response).getInteger("Code") == Constant.Succeed){

            boolean isForceUpdate = false;
            isForceUpdate = JSON.parseObject(response).getJSONObject("Result").getBoolean("isForceUpdate");

            String downloadUrl = JSON.parseObject(response).getJSONObject("Result").getString("url");
            String updateMsg = JSON.parseObject(response).getJSONObject("Result").getString("remark");

            Bundle bundle=new Bundle();
            bundle.putBoolean("isForceUpdate",isForceUpdate);
            showVersionDialog(downloadUrl,"新版本更新",updateMsg,bundle);
        }
    }
}
