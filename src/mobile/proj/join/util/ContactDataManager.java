/* 작성일 : 2014년12월14일
 * 작성자 : 정혜윤
 * 클래스 설명 : 회원의 정보관련 DB를 다루는  Manager searchInfo를 통해 db에 저장된 회원정보를 가져오고 addContact를 통해 새로운 정보를 등록함
 */
package mobile.proj.join.util;

import java.util.ArrayList;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class ContactDataManager {
	private final static String TABLE_NAME = "contact_table";
	private ContactDbHelper helper = null;
	public ListView lvContacts = null;
	public ArrayAdapter<ContactDto> adapter = null;
	ContactDto dto = new ContactDto();
	
	public ContactDataManager(Context context) {
		helper = new ContactDbHelper(context);
	}

	//정보 검색
	public ContactDto searchInfo() { 
		ContactDto dto = new ContactDto();
		SQLiteDatabase db = helper.getReadableDatabase();
		
		
		Cursor cursor = db.rawQuery("SELECT * FROM contact_table;", null);
		
		while (cursor.moveToNext()){
			dto.setId(cursor.getString(0));
			dto.setPw(cursor.getString(1));
			dto.setPhone(cursor.getString(2));
			dto.setEmail(cursor.getString(3));
		}
		cursor.close();
		helper.close();
		return dto;
		
	}

//	새로운 연락처 입력
//	연락처 정보 dto 를 전달받아 DB 에 저장
	public void addContact(ContactDto dto) {
		SQLiteDatabase db = helper.getWritableDatabase();
//		매개변수로 받은 dto 객체의 값을 사용하여 query 문장 생성
		String query = "INSERT INTO " + TABLE_NAME + " VALUES ('"+ dto.getId() + "', '" + dto.getPw() + "', '" + dto.getPhone()+ "', '" + dto.getEmail()+"');";
//		Logcat 창에서 query 문을 살펴볼 수 있도록 출력
		Log.i("ContactDataManager", "SQL: " + query);
		
		db.execSQL(query);
		db.close();
	}
	
	
	public void deleteList() {
		SQLiteDatabase db = helper.getWritableDatabase();
		String sql = "DELETE FROM contact_table;";
		db.execSQL(sql);
		
		db.close();
		}

}

	
	
