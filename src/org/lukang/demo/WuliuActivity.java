package org.lukang.demo;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.lukang.demo.findbook.CaptureActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * 
 * @author lukang
 *
 */

public class WuliuActivity extends Activity {

    private EditText       edt_wuliu_user_id;
    private EditText       edt_wuliu_id;
    private EditText       edt_company_key;
    private TextView       wuliuInfoTv;
    
    private WuliuInfo     wuliuInfo;


    private ProgressDialog pd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wuliuinfo);

        edt_wuliu_user_id = (EditText) findViewById(R.id.edt_wuliu_user_id);
        edt_wuliu_id = (EditText) findViewById(R.id.edt_wuliu_id);
        edt_company_key = (EditText) findViewById(R.id.edt_company_key);
        
        wuliuInfoTv = (TextView) findViewById(R.id.wuliu_info);


        Button btn_wuliu_user = (Button) findViewById(R.id.btn_wuliu_user);
        Button btn_wuliu_user_info = (Button) findViewById(R.id.btn_wuliu_user_info);
        Button btn_fetch_wuliu_info = (Button) findViewById(R.id.btn_fetch_wuliu_info);
        Button btn_scan_wuliu_qrcode = (Button) findViewById(R.id.btn_scan_wuliu_qrcode);
        Button btn_company_key_search = (Button) findViewById(R.id.btn_company_key_search);


        btn_scan_wuliu_qrcode.setOnClickListener(btnlistener);
        
        btn_fetch_wuliu_info.setOnClickListener(btnlistener);


    }

    public View.OnClickListener btnlistener = new View.OnClickListener() {

                                                @Override
                                                public void onClick(View v) {
                                                    // TODO Auto-generated method stub

                                                    if (!NetUtils.isNetworkOk(WuliuActivity.this))
                                                        return;

                                                    switch (v.getId()) {
                                                        
                                                        case R.id.btn_scan_wuliu_qrcode:
                                                            Intent i = new Intent(
                                                                WuliuActivity.this,
                                                                CaptureActivity.class);
                                                            startActivityForResult(i, 0);
                                                            break;
                                                        case R.id.btn_fetch_wuliu_info:
                                                            EditText edt = (EditText) findViewById(R.id.edt_wuliu_id);
                                                            EditText edt_company = (EditText) findViewById(R.id.edt_company_key);
                                                            fetchWuliuInfo(edt.getText().toString(),edt_company.getText().toString());
                                                            break;
                                                    }
                                                }
                                            };


    /**
     * @param id
     * @param company
     */
    protected void fetchWuliuInfo(String id,String company) {
        // TODO Auto-generated method stub
        pd = new ProgressDialog(this);
        pd.setMessage("正在从物流平台获取信息...");
        pd.show();
        HttpTaskListener wuliuInfoListener = new HttpTaskListener(HttpListener.GET_WULIUINFO);
        WuliuInfoUtils.getWuliuInfo(StringUtils.trim(id), StringUtils.trim(company), wuliuInfoListener);

    }
    



    /**
     * 根据userId获取用户信息
     * @param userid
     */
    protected void fetchUserInfo(String userid) {
        pd = new ProgressDialog(this);
        pd.setMessage("正在获取用户信息...");
        pd.show();

    }



    /**
     * @param keyword
     */
    protected void searchWuliuCompany(String keyword) {
        pd = new ProgressDialog(this);
        pd.setMessage("正在从物流平台搜索相关公司...");
        pd.show();
        HttpTaskListener booksearchListener = new HttpTaskListener(HttpListener.SEARCH_BOOKS);
        DoubanBookUtils.searchBooks(keyword, booksearchListener);
    }

    /**
     * 
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("DEBUG", "onNewIntent---");

    }

    /**
     * Http请求监听器，用于处理HttpAsyncTask中的响应事件
     * @author tinyao
     *
     */
    private class HttpTaskListener implements HttpListener {

        int type;

        public HttpTaskListener(int type1) {
            this.type = type1;
        }

		@SuppressWarnings("unchecked")
		@Override
		public void onTaskCompleted(Object data) {
			switch (type) {
			case HttpListener.GET_WULIUINFO:
				if (pd != null)
                    pd.dismiss();
				wuliuInfo = WuliuInfoUtils.parseWuliuInfo(String.valueOf(data));
                wuliuInfoTv.setText("物流信息:\n" + wuliuInfo);
                wuliuInfoTv.append("\n经过地点:\n" + WuliuInfoUtils.getLocations(wuliuInfo));
			}
		}

        @Override
        public void onTaskFailed(String data) {
            // TODO Auto-generated method stub
            Toast.makeText(WuliuActivity.this, "error: " + data, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        if (resultCode == RESULT_OK) {
            //String codeFormat = data.getStringExtra("bracode_format");
            String codeText = data.getStringExtra("bracode_text");
            
            if (codeText!=null && !"".equals(codeText)) {
            	edt_wuliu_id.setText(codeText);
                //fetchBookInfo(codeText);
            } else {
                Toast.makeText(this, "没有找到运单号", Toast.LENGTH_SHORT).show();
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}
