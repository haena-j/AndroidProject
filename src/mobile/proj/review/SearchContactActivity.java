/* 작성일 : 2014년12월14일
 * 작성자 : 정혜윤
 * 클래스 설명 :리뷰검색액티비티. 영화이름으로 혹은 별점으로 검색
 */
package mobile.proj.review;


import java.util.ArrayList;

import mobile.proj.join.R;
import mobile.proj.review.util.ContactDataManager;
import mobile.proj.review.util.ContactDto;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SearchContactActivity extends Activity {
	
	ContactDataManager contact = new ContactDataManager(SearchContactActivity.this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_contact);
	}
	
	public void onClick(View v){
		ArrayList<ContactDto> contactList = new ArrayList<ContactDto>();
		ContactDto dto = new ContactDto();
		EditText editText = (EditText)findViewById(R.id.newNameEdit);
		TextView textView = (TextView)findViewById(R.id.searchResultText);
		RadioGroup menu = (RadioGroup)findViewById(R.id.menu); //이름과 분류중 선택할 수있는 radiogroup
		
		if( menu.getCheckedRadioButtonId() == R.id.radioButton1){ //영화이름으로 영화검색
			
			dto = contact.searchByTitle(editText.getText().toString());
			if(dto.getTitle() == null){ //영화의 리뷰가없을 경유
				Toast.makeText(this, "영화 [ " + editText.getText() + "] 의 리뷰가 등록되어있지 않습니다.", Toast.LENGTH_SHORT).show();
			}	 
				
			else{	
				String s = "영화제목: " + dto.getTitle() + "\n평점 : " + dto.getUserRating() + "점\n후기 : " + dto.getReview()+ "\n\n\n";
				textView.setText(s);
			}
		}
		else {//평점로 연락처 검색 3점입력시 3점에 해당하는 모든정보 출력
			
			
			contactList = contact.searchByUserRating(editText.getText().toString());
		
				
			String s = "";
			for(ContactDto dto1 : contactList){
				s += "영화제목: " + dto1.getTitle() + "\n평점 : " + dto1.getUserRating() + "점\n후기 : " + dto1.getReview() + "\n\n\n";
			}
			textView.setText(s);
			
			if(s == "")//일치하는 평점의 리뷰가 없을경우
				Toast.makeText(this, "평점 " + editText.getText() +" 점인 리뷰가 등록되어있지 않습니다.", Toast.LENGTH_SHORT).show();
			
	}
}
	
}

