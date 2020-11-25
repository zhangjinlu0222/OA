package zjl.com.oa2.Base;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import zjl.com.oa2.ApplicationConfig.Constant;

/**
 * Created by Administrator on 2018/4/23.
 */

public class ModelImpl {
    private static final OkHttpClient client = new OkHttpClient.Builder().
            connectTimeout(20, TimeUnit.SECONDS).
            readTimeout(20, TimeUnit.SECONDS).
            writeTimeout(20, TimeUnit.SECONDS).build();

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build();
}
