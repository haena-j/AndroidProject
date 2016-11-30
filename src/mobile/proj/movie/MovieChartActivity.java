/* 작성일 : 2014년12월14일
 * 작성자 : 정혜윤
 * 클래스 설명 : 영화순위정보. kobis의 영화순위 api로 영화정보를 가져온뒷 db에 저장하고,  naver 영화 검색 api를 이용해 영화이름을 통해 영화 이미지를 검색해서 db에 update 해준다.
 * 그다음 db에 저장된 순위들을 보여준다. 목록 클릭시 알람설정기능 alertDialog를 이용해서 구현 
 */package mobile.proj.movie;

import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import mobile.proj.join.R;
import mobile.proj.movie.AlarmBR;
import mobile.proj.movie.util.Custom_MovieList_Adapter;
import mobile.proj.movie.util.ImageNetworkManager;
import mobile.proj.movie.util.MovieDataManager;
import mobile.proj.movie.util.MovieDto;
import mobile.proj.movie.util.MovieXMLParser;
import mobile.proj.movie.util.NetworkManager;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MovieChartActivity extends Activity implements OnItemClickListener{
	public final static String TAG = "AlarmTest";
	private ArrayList<MovieDto> resultList;
	private ArrayList<MovieDto> list;
	private String resultXml;
	private Custom_MovieList_Adapter adapter;
	private ListView lvResult; //ListView
	AlarmManager alarmManager;
	ImageView iv;
	URL imageUrl;
	Bitmap bm;
	String url2 = "";
	String image = "";
	String movie = "";
	int mSelect = 0;
	NetworkManager netManager;
	Calendar cal;
	Calendar calendar;
	MovieDataManager movieManager = new MovieDataManager(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.moviechart);
		
		lvResult = (ListView)findViewById(R.id.MovielistView); //ListView
		lvResult.setOnItemClickListener(this); //클릭 리스너 설정
		iv = (ImageView)findViewById(R.id.iVMovie);
		list = new ArrayList<MovieDto>();
		movieManager.deleteDb();
		calendar = Calendar.getInstance(); 
		calendar.add(Calendar.DATE, -1); //영화순위 기준을 오늘에서 하루를 뺀 날 값으로 설정하기 위한 calendar 객체
		String day = new SimpleDateFormat("yyyyMMdd").format(calendar.getTime()); //calendar의 형식을 kobis api의 검색조건에 맞게 Format사용
		String url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.xml?key=c92c924f331ffa6da0772bbd71f0de5e&targetDt=";
		url += day;
		NetworkManager netManager = new NetworkManager(this,mlistXmlHandler,url);
		netManager.setDaemon(true);
		netManager.start();
		

		
	}
	
	Handler mlistXmlHandler = new Handler() {
		public void handleMessage(Message msg) { 
			resultXml = (String) msg.obj;
			MovieXMLParser parser = new MovieXMLParser();
			resultList = parser.parse(resultXml);

			for(MovieDto dto : resultList) {//영화 이름으로 네이버 api를 이용해 image 검색
				movieManager.insertNewMovie(dto);
				String url2 = "http://openapi.naver.com/search?key=f20b87f0a977af2c53cb3019b6316da7&target=movie&display=1&query=";
				url2 += encodeKorean(dto.getMovieNm());
				
				//NetworkManager를 사용중 이므로 새로움 Manager구현
				ImageNetworkManager imageNetManager = new ImageNetworkManager(MovieChartActivity.this, mHandler, url2, dto.getMovieNm());
				imageNetManager.setDaemon(true);
				imageNetManager.start(); // image update
				
			}
		}
	};

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) { 
			movieManager = (MovieDataManager)msg.obj;
			
			list = movieManager.getAllLocations();//movie List를 가져와  ListView에 보여준다.
			adapter = new Custom_MovieList_Adapter(MovieChartActivity.this,  android.R.layout.list_content, list);
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

		new AlertDialog.Builder(this) //클릭시 영화예매시간알림을 위한 AlertDialog
		.setTitle("영화예약 시간 알림설정 : 시간을 선택하세요")
		.setIcon(R.drawable.alarmmark)
		.setSingleChoiceItems(R.array.times, mSelect, //res의 values의 arrays에 시간정보 저장되어있음
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				mSelect = which; //선택 번호 0부터!
				
			}
		})
		.setPositiveButton("확인", new DialogInterface.OnClickListener() { //확인버튼누를경우
			public void onClick(DialogInterface dialog, int whichButton) {
				alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE); //알람매니저

				cal = Calendar.getInstance(); 
				cal.setTimeInMillis(System.currentTimeMillis()); //TimeInMillis로 변경
				Intent intent = new Intent(MovieChartActivity.this, AlarmBR.class); 
				PendingIntent pIntent = PendingIntent.getBroadcast(MovieChartActivity.this, 0, intent, 0);//pendingIntent설정
				switch(mSelect){
				case 0:
					cal.add(Calendar.SECOND, 10); //현재시간에 10초를 더한다
					Log.i("time:",cal.toString());
					alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pIntent); //알람등록
					break;
				case 1:
					cal.add(Calendar.MINUTE, 30);
					alarmManager.set(AlarmManager.RTC, cal.getTimeInMillis(), pIntent);
					break;
				case 2:
					cal.add(Calendar.HOUR, 1);
					alarmManager.set(AlarmManager.RTC, cal.getTimeInMillis(), pIntent);
					break;
				case 3:
					cal.add(Calendar.HOUR, 2);
					alarmManager.set(AlarmManager.RTC, cal.getTimeInMillis(), pIntent);
					break;	
				}
			}
		})
		.setNegativeButton("취소", null)
		.show();
		
	}

}