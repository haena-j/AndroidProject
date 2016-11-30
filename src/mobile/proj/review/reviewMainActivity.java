/* 작성일 : 2014년12월14일
 * 작성자 : 정혜윤
 * 클래스 설명 : review함 클릭시. Intent를 통해 리뷰보기, 리뷰작성, 리뷰검색으로 넘겨짐
 */
package mobile.proj.review;

import mobile.proj.join.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class reviewMainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.review_activity_main);
	}
	
	public void onClick(View v) {
		Intent intent = null;
		
		switch (v.getId()) {
		case R.id.openAllListBtn: //리뷰 보기
			intent = new Intent(this, AllContactsActivity.class);
			break;
		case R.id.openInsertionBtn: //리뷰작성
			intent = new Intent(this, InsertContactActivity.class);
			break;
		case R.id.openSearchBtn: //리뷰검색
			intent = new Intent(this, SearchContactActivity.class);
			break;
		}
		
		if (intent != null) startActivity(intent);
	}
	
}
