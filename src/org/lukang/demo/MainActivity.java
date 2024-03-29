package org.lukang.demo;

import org.lukang.demo.map.ItemizedOverlayDemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button doubanBtn = (Button)findViewById(R.id.to_douban_act);
		doubanBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, DoubanActivity.class);
				startActivity(i);
			}
			
		});
		
		Button wuliuBtn = (Button)findViewById(R.id.to_wuliu_act);
		wuliuBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, WuliuActivity.class);
				startActivity(i);
			}
			
		});
		
		Button sinaBtn = (Button)findViewById(R.id.sina_place);
		sinaBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, SinaPlaceActivity.class);
				startActivity(i);
			}
			
		});
		
		Button baiduBtn = (Button)findViewById(R.id.baidu_location);
		baiduBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, MyLocation.class);
				startActivity(i);
			}
			
		});
		
		Button itemBtn = (Button)findViewById(R.id.item_location);
		itemBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, ItemizedOverlayDemo.class);
				startActivity(i);
			}
			
		});

	}

	
	public boolean isNetworkOk() {
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		} else {
			Toast.makeText(this, "Network is disconnected", Toast.LENGTH_SHORT).show();
			return false;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
