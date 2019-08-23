package com.example.androidokhttp;

import java.io.IOException;

import com.example.androidokhttp.R;
import com.example.okhttputils.CallBackUtil;
import com.example.okhttputils.OkhttpUtil;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";
	Button button;
	TextView mTextview;
	String get;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTextview = (TextView) findViewById(R.id.textname);
		button = (Button) findViewById(R.id.Button1);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			getOne();
	
			//getTwo();
			}
		});
	}
		
	//δ��װ
	public void getTwo(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				String Url = "http://www.baidu.com";
				try {
					get = getSync(Url);
					new Handler(Looper.getMainLooper()).post(new Runnable() {
						public void run() {
							mTextview.setText(get);
						}
					});
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	//��װ��,�첽
	public void getOne(){
    String url = "https://www.baidu.com/";
    OkhttpUtil.okHttpGet(url, new CallBackUtil.CallBackString() {
        @Override
        public void onFailure(Call call, Exception e) {
    		Log.d(TAG, "----------e-"+e.toString());
        }
        @Override
        public void onResponse(String response) {
        	mTextview.setText(response);
        }
    });
	}
	

	/* ͬ����Get���� */
	public static String getSync(String url) throws IOException {
		Log.d(TAG, "----getSync------ͬ��-");
		// ����OKHttpClient����
		OkHttpClient okHttpClient = new OkHttpClient();
		// ����һ��Request
		final Request request = new Request.Builder().url(url).build();
		Call call = okHttpClient.newCall(request);
		// ����ֵΪresponse
		Response response = call.execute();
		// ��responseת����String
		String responseStr = response.body().string();
		Log.d(TAG, "----ͬ����Get-------" + responseStr);
		Log.d(TAG, "-----response.code()==" + response.code());
		return responseStr;
	}

	
 //�첽��Get����
	public static void getAsyn(String url) {
		Log.d(TAG, "----getAsyn------�첽-");
		// ����OKHttpClient����
		OkHttpClient okHttpClient = new OkHttpClient();
		// ����һ��Request
		final Request request = new Request.Builder().url(url).build();
		Call call = okHttpClient.newCall(request);
		// ����������
		call.enqueue(new Callback() {
			// ����ʧ�ܵĻص�
			@Override
			public void onFailure(Call call, IOException e) {
				e.printStackTrace();
			}
			// ����ɹ��Ļص�
			@Override
			public void onResponse(Call call, Response response) throws IOException {
				// ��responseת����String
				String responseStr = response.body().string();
				Log.d(TAG, "----�첽��Get-------" + responseStr);
			}
		});
	}

	@Override
	public void onBackPressed() {
		finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
