/* 작성일 : 2014년12월14일
 * 작성자 : 정혜윤
 * 클래스 설명 : 리뷰관련 DBHelper
 */
package mobile.proj.review.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContactDbHelper extends SQLiteOpenHelper {
	private final static String DATABASE_NAME = "review_db";
	private final static String TABLE_NAME = "review_table";
	
	public ContactDbHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
//		테이블 생성 query
		String query = "CREATE TABLE " + TABLE_NAME +
						"( no integer primary key autoincrement, title text, userRating text, review text)";
		db.execSQL(query);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
