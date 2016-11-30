/* 작성일 : 2014년12월14일
 * 작성자 : 정혜윤
 * 클래스 설명 : 회원정보관련 DBHelper db table를 새로 만들어준다.
 */
package mobile.proj.join.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContactDbHelper extends SQLiteOpenHelper {
	private final static String DATABASE_NAME = "contact_db";
	private final static String TABLE_NAME = "contact_table";
	
	public ContactDbHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
//		테이블 생성 query
		String query = "CREATE TABLE " + TABLE_NAME +
						"( id TEXT primary key, pw TEXT,  phone TEXT, email TEXT)";
		db.execSQL(query);
		
		
	
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
