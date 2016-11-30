/* 작성일 : 2014년12월14일
 * 작성자 : 정혜윤
 * 클래스 설명 : 영화검색에서 리뷰작성기능 클릭시. 영화제목은 intent로 받아온 값을 그대로 사용
 */
package mobile.proj.review;

import mobile.proj.join.R;
import mobile.proj.review.util.ContactDataManager;
import mobile.proj.review.util.ContactDto;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChoiceInsertContactActivity extends Activity {
	private TextView tvTitle;
	private EditText et2;
	private EditText et3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choice_insert_contact);
		String title = getIntent().getStringExtra("title"); //intent로 넘겨받은 값. 영화제목
		tvTitle = (TextView)findViewById(R.id.tvName);
		et2 = (EditText)findViewById(R.id.etPhone);
		et3 = (EditText)findViewById(R.id.etCategory); 
		tvTitle.setText(title);
	}
	
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.saveNewContactBtn: //저장버튼 누를 경우
			
			ContactDto dto = new ContactDto();
			dto.setTitle(tvTitle.getText().toString());
			dto.setUserRating(et2.getText().toString());
			dto.setReview(et3.getText().toString());
			
//			DataManager
			ContactDataManager manager = new ContactDataManager(this); 
			manager.addContact(dto); //reviewdb에 저장
			Toast.makeText(this, dto.getTitle() + "의 리뷰가 등록되었습니다.", Toast.LENGTH_SHORT).show(); //Toast로 안내
			break;
		case R.id.saveCancelBtn:
			finish();
			break;
		}
	}
}
