/* 작성일 : 2014년12월14일
 * 작성자 : 정혜윤
 * 클래스 설명 :무비함 관련 디비핼퍼
 */
package mobile.proj.search.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ScrapDBHelper extends SQLiteOpenHelper{
	private final static String DATABASE_NAME = "scrap_db";
	private final static String TABLE_NAME = "myScrap_table";
	
//	생성자 수행 시 데이터베이스를 저장할 파일명(DATABASE_NAME) 지정
//	생성한 파일은 DDMS의 file explorer에서 data/data/기본패키지명/databases에서 확인 가능 (에뮬레이터만 접근 가능)
	public ScrapDBHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}

	
//	DB 요청 시 DB 가 없을 경우 처음 한 번만 실행됨
	@Override
	public void onCreate(SQLiteDatabase db) {
//		테이블 생성 query
		String query = "CREATE TABLE " + TABLE_NAME +
						"( title text, subtitle text, pubDate text, director text, actor text, userRating text, image text)";
		
		db.execSQL(query);
		
	}

	
//	DB 업그레이드 요청 시 -생성자의 버전번호를 바꿀 때- 호출
//	지금 예제의 경우 불필요
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
}
