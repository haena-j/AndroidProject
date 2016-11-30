/* 작성일 : 2014년12월14일
 * 작성자 : 정혜윤
 * 클래스 설명 : 첫 페이지에서 회원가입 버튼 누를시 실행되는 부분. 비밀번호확인을통해 일치하는지 확인 및 연락처 길이 확인
 */
package mobile.proj.join;

import mobile.proj.join.R;
import mobile.proj.join.util.ContactDataManager;
import mobile.proj.join.util.ContactDto;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class JoinActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.join);
		
	}
	
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.joinSaveBtn:
 
			EditText etId = (EditText)findViewById(R.id.joinId);
			EditText etPw = (EditText)findViewById(R.id.joinPw);
			EditText etPw2 = (EditText)findViewById(R.id.joinPw2); 
			EditText etNum = (EditText)findViewById(R.id.joinNum); 
			EditText etEmail = (EditText)findViewById(R.id.joinEmail); 
		
			String pw1 = etPw.getText().toString();
			String pw2 = etPw2.getText().toString();
			int num_phone = Integer.parseInt(etNum.getText().toString());
			String phone = etNum.getText().toString();
			int length = (int)(Math.log10(num_phone) + 1);//phone 길이계산
			if(!pw1.equals(pw2))
				Toast.makeText(this, "비밀번호가 일치하지 않습니다.\n다시입력해주세요.", Toast.LENGTH_SHORT).show();
			else if(length != 10 && length != 11)
				Toast.makeText(this, "연락처는 10~11자리여야합니다.", Toast.LENGTH_SHORT).show();
			else{
			ContactDto dto = new ContactDto();
			dto.setId(etId.getText().toString());
			dto.setPw(pw1);
			dto.setPhone(phone);
			dto.setEmail(etEmail.getText().toString());
			
//			DataManager
			ContactDataManager manager = new ContactDataManager(this);
			manager.addContact(dto); 
		
			Toast.makeText(this, "가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(JoinActivity.this, MainActivity.class);
			startActivity(intent);
			}
			break;
		case R.id.joinCancelBtn:
			finish();
			break;
		}
	}
}
