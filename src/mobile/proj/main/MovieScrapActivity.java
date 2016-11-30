/* 작성일 : 2014년12월14일
 * 작성자 : 정혜윤
 * 클래스 설명 : 영화검색기능에서 스크랩한 영화들의 목록을 보여준다. 클릭시 삭제기능 ScrapDataManager를 통해 DB에 저장된 값들을 가져온다
 */
package mobile.proj.main;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import mobile.proj.join.R;
import mobile.proj.join.util.ContactDto;
import mobile.proj.movie.util.MovieDataManager;
import mobile.proj.movie.util.MovieDto;
import mobile.proj.search.ItemClickActivity;
import mobile.proj.search.SearchActivity;
import mobile.proj.search.util.Custom_List_Adapter;
import mobile.proj.search.util.Custom_ScrapList_Adapter;
import mobile.proj.search.util.ItemDto;
import mobile.proj.search.util.MyItemDto;
import mobile.proj.search.util.ScrapDataManager;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MovieScrapActivity extends Activity implements OnItemClickListener{
	private ArrayList<MyItemDto> resultList;
	ListView lvResult;
	private Custom_ScrapList_Adapter adapter;
	ScrapDataManager manager = new ScrapDataManager(this);
	URL imageUrl;
	Bitmap bm;
	ImageView iv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movie_scrap_list);
		lvResult = (ListView)findViewById(R.id.scrapListView);
		resultList = manager.getAllLocations();
		iv =  (ImageView)findViewById(R.id.iVMovie);
		
		lvResult.setOnItemClickListener(this); //목록 클릭시를 위한 리스너사용
		adapter = new Custom_ScrapList_Adapter(MovieScrapActivity.this, android.R.layout.list_content, resultList);//직접만든 커스텀어댑터뷰사용
		lvResult.setAdapter(adapter);
				
			if(MyThread.getState() == Thread.State.NEW)
				MyThread.start();
			
		}

			Thread MyThread = new Thread(new Runnable(){//image같은 경우 네트워크를 사용하므로 예외처리및 쓰레드 사용이 필요하다
			@Override
			public void run(){
			for(MyItemDto itemDto : resultList){
				try{
					imageUrl = new URL(itemDto.getImage());
					HttpURLConnection con = (HttpURLConnection)imageUrl.openConnection();
					BufferedInputStream bis = new BufferedInputStream(con.getInputStream(), 10240);
					bm = BitmapFactory.decodeStream(bis);
					iv.setImageBitmap(bm);
					
					bis.close();    
				   }catch(Exception e){e.printStackTrace();}
				}
			}

			});
		

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {//목록클릭시
				MyItemDto data = (MyItemDto)parent.getItemAtPosition(position);//클릭한 data를 가져옴
				manager.deleteDb(data.getTitle());//삭제
				Toast.makeText(this, "영화  [" + data.getTitle() + "] 을(를) 삭제합니다.",Toast.LENGTH_SHORT).show();
			}
	
	}
	
