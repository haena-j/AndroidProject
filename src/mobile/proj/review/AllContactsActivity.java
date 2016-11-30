/* 작성일 : 2014년12월14일
 * 작성자 : 정혜윤
 * 클래스 설명 : 모든 review 정보를 가져오는 Activitiy longClick시 삭제AlertDialong가 발생
 */
package mobile.proj.review;

import java.util.ArrayList;

import mobile.proj.join.R;
import mobile.proj.review.util.ContactDataManager;
import mobile.proj.review.util.ContactDto;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class AllContactsActivity extends Activity {
	
	ArrayList<ContactDto> contactList = null;
	private ListView lvContacts = null;
	ArrayAdapter<ContactDto> adapter = null;
	ContactDataManager contact = new ContactDataManager(AllContactsActivity.this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_contacts);
		
		lvContacts = (ListView)findViewById(R.id.allContactsList);

		//저장된 review List를 가져와 ListView로 보여줌
		contactList = contact.getAllContacts();
		adapter = new ArrayAdapter<ContactDto>(this, android.R.layout.simple_list_item_1, contactList);		
		lvContacts.setAdapter(adapter);
		
		
		
		lvContacts.setOnItemLongClickListener (new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> arg0, View v, final int position, long arg3) {
				
				new AlertDialog.Builder(AllContactsActivity.this)//삭제확인을 위한 Alert
				.setTitle("삭제하시겠습니까?")
				.setIcon(R.drawable.alarmmark)
				.setPositiveButton("확인", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						ContactDto dto = contactList.get(position);
						contact.deleteList(dto.getNo());
						contactList.remove(position);
						Toast.makeText(AllContactsActivity.this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
						adapter.notifyDataSetChanged();
						
					}
					
				})
	
				.setNegativeButton("취소", null)
				.show();
				return true;
							
			}

		});
	
	}
}
