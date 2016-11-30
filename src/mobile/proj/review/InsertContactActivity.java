/* 작성일 : 2014년12월14일
 * 작성자 : 정혜윤
 * 클래스 설명 : 리뷰함에서 리뷰작성기능 클릭시. 영화제목은 새로 입력 받아 저장
 */
package mobile.proj.review;

import mobile.proj.join.R;
import mobile.proj.review.util.ContactDataManager;
import mobile.proj.review.util.ContactDto;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class InsertContactActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_insert_contact);
	}
	
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.saveNewContactBtn:

			EditText et1 = (EditText)findViewById(R.id.etName);
			EditText et2 = (EditText)findViewById(R.id.etPhone);
			EditText et3 = (EditText)findViewById(R.id.etCategory); 
			

			ContactDto dto = new ContactDto();
			dto.setTitle(et1.getText().toString());
			dto.setUserRating(et2.getText().toString());
			dto.setReview(et3.getText().toString());
			
//			DataManager
			ContactDataManager manager = new ContactDataManager(this);
			manager.addContact(dto); 
			Toast.makeText(this, dto.getTitle() + "의 리뷰가 등록되었습니다.", Toast.LENGTH_SHORT).show();
			break;
		case R.id.saveCancelBtn:
			finish();
			break;
		}
	}
}
