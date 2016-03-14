/**
 * CopyRight Haoguang Xu
 */
package com.example.qrcode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.zxing.WriterException;
import com.zxing.activity.CaptureActivity;
import com.zxing.encoding.EncodingHandler;

import android.app.Activity;
import android.content.Intent;
import android.database.CursorJoiner.Result;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button btnScan;
	private TextView tvResult;
	
	private Button btnGetQrcode;
	private EditText etInput;
	private ImageView imgQrode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnScan = (Button) findViewById(R.id.btnScan);
		tvResult = (TextView) findViewById(R.id.tvResult);
		btnScan.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, "你写可以扫描条形码或者二维码", Toast.LENGTH_SHORT).show();
				Intent startScan = new Intent(MainActivity.this, CaptureActivity.class);
				// startActivity(startScan);
				startActivityForResult(startScan, 0);
			}
		});
		
		
		btnGetQrcode = (Button) findViewById(R.id.btnGetQrcode);
		imgQrode = (ImageView) findViewById(R.id.imgQrcode);
		etInput = (EditText) findViewById(R.id.etInput);
		
		btnGetQrcode.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String string = etInput.getText().toString();
				if (string.equals("")) {
					Toast.makeText(MainActivity.this, "请输入二维码文本内容", Toast.LENGTH_SHORT).show();
					imgQrode.setImageBitmap(null);
				}else{
					try {
						Bitmap bitmap = EncodingHandler.createQRCode(string, 1000);
						imgQrode.setImageBitmap(bitmap);
					} catch (WriterException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			String result = data.getExtras().getString("result");

			String pattern = "http*";
			Pattern r = Pattern.compile(pattern);
			Matcher m = r.matcher(result);

			if (m.find()) {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(result));
				startActivity(intent);
			} else {
				tvResult.setText(result);
			}
		}
	}
	
}
