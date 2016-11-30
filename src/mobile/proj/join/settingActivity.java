/* 작성일 : 2014년12월14일
 * 작성자 : 정혜윤
 * 클래스 설명 : 설정창 부분. WifiManager를 사용해 와이파이 on/off 기능 추가. 내정보수정 기능 구현.
 */
package mobile.proj.join;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;
import android.widget.ToggleButton;


public class settingActivity extends Activity {
ToggleButton option;
private WifiManager wifiManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		option = (ToggleButton)findViewById(R.id.ToggleButton1);
		option.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(!isChecked){
					wifiManager.setWifiEnabled(false);
					Toast.makeText(settingActivity.this, "WIFI가 해제되었습니다.", Toast.LENGTH_SHORT).show();
				}
				else{
					 wifiManager.setWifiEnabled(true);
					 Toast.makeText(settingActivity.this, "WIFI가 설정되었습니다.", Toast.LENGTH_SHORT).show();
				}
			}
			
		});
	}


	public void onClick(View v){
		Intent intent = null;
		
		switch(v.getId()){
		case R.id.btnChangeInfo:
			intent = new Intent(this, ChangeInfoActivity.class);
			break;
		
		}
		if (intent != null) startActivity(intent);
		
	}
	


}
