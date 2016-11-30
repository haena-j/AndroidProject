/* 작성일 : 2014년12월14일
 * 작성자 : 정혜윤
 * 클래스 설명 : 처음 어플실행시 실행되는 부분. DB에 사용자의 정보가 저장되있을 경우 정보등록이 안되고 정보등록이안된채 로그인을 하려고하면 정보등록을 먼저 해달라고함
 */
package mobile.proj.join;

import mobile.proj.join.util.ContactDataManager;
import mobile.proj.join.util.ContactDto;
import mobile.proj.main.MainFormActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	EditText pwEt;
	ContactDto dto = new ContactDto();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		pwEt = (EditText)findViewById(R.id.pw_et);
		
	}


	public void onClick(View v){
		Intent intent = null;
		ContactDataManager manager = new ContactDataManager(this);
		dto = manager.searchInfo();
		switch(v.getId()){
		case R.id.loginBtn:
			
			if(pwEt.getText().toString().equals(dto.getPw()))
				intent = new Intent(this, MainFormActivity.class);
			else if(dto.getPw() == null)
				Toast.makeText(MainActivity.this, "정보등록을 먼저해주세요.", Toast.LENGTH_SHORT).show();
			else
				Toast.makeText(MainActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
			break;
		case R.id.joinBtn:
			if(dto.getId() != null){
				Toast.makeText(this, dto.getId() + "님 로그인을 해주세요", Toast.LENGTH_SHORT).show();
				break;
			}
			intent = new Intent(this, JoinActivity.class);
			break;
		}
		if (intent != null) startActivity(intent);
	}
	

}
