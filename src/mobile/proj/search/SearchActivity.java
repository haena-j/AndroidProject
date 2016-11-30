/* 작성일 : 2014년12월14일
 * 작성자 : 정혜윤
 * 클래스 설명 : 영화검색 액티비티. 네이버 영화 검색 api 이용
 */
package mobile.proj.search;


import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import mobile.proj.join.R;
import mobile.proj.search.SearchActivity;
import mobile.proj.search.util.Custom_List_Adapter;
import mobile.proj.search.util.ItemDataManager;
import mobile.proj.search.util.ItemDto;
import mobile.proj.search.util.MyXMLParser;
import mobile.proj.search.util.NetworkManager;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SearchActivity extends Activity implements OnItemClickListener{

	private ArrayList<ItemDto> resultList;
	private String resultXml;
	private ListView lvResult;
	private String mServerUrl;
	private TextView tvReceivedXml;
	private Custom_List_Adapter adapter;
	ItemDataManager itemManager = new ItemDataManager(this);
	ImageView iv;
	URL imageUrl;
	Bitmap bm;
	Handler handler = new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_main);
		
		resultList = new ArrayList<ItemDto>();
		tvReceivedXml = (TextView)findViewById(R.id.editText1);
		lvResult = (ListView)findViewById(R.id.lvResult);
		iv =  (ImageView)findViewById(R.id.iVMovie);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		lvResult.setOnItemClickListener(this);
	}
	
	
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btnGetXML:
			String str = tvReceivedXml.getText().toString();
//			XML을 요청
			mServerUrl = "http://openapi.naver.com/search?key=f20b87f0a977af2c53cb3019b6316da7&target=movie&display=20&start=1&query=";
			mServerUrl += encodeKorean(str);
			//naver 영화 검색 api이용 NetworManager 사용
			NetworkManager netManager = new NetworkManager(this,mXmlHandler,mServerUrl);
			netManager.setDaemon(true);
			netManager.start();
			
			break;
		
		}
	}

	Handler mXmlHandler = new Handler() {
		public void handleMessage(Message msg) { 
			resultXml = (String) msg.obj;
			MyXMLParser parser = new MyXMLParser();
			//검색결과를 ListView에 출력
			resultList = parser.parse(resultXml);
			adapter = new Custom_List_Adapter(SearchActivity.this, android.R.layout.list_content, resultList);
			lvResult.setAdapter(adapter);
			
		}
	};
	
	private String encodeKorean(String target) {
		String encodedTarget = null;
		try  {
			encodedTarget = URLEncoder.encode(target, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encodedTarget;
    }
	
	
	public void onItemClick (AdapterView<?> parent, View v, int position, long id){
		//검색목록을 클릭할 경우 결과 값을 Intent에 담아 보낸다
		ItemDto data = (ItemDto)parent.getItemAtPosition(position);
		itemManager.inserMyMovie(data);
		Bundle extras = new Bundle();
		extras.putString("title", data.getTitle());
		extras.putString("image", data.getImage());
		extras.putString("subTitle", data.getSubtitle());
		extras.putString("userRating", data.getUserRating());
		extras.putString("pubData", data.getPubDate());
		extras.putString("director", data.getDirector());
		extras.putString("actor", data.getActor());
		
		Intent intent = new Intent(this, ItemClickActivity.class);
		intent.putExtras(extras);
		startActivity(intent);
	}
}

