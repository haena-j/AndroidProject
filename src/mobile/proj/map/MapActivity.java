/* 작성일 : 2014년12월14일
 * 작성자 : 정혜윤
 * 클래스 설명 : 지도검색 기능. daum api로 영화관 정보를 검색하고, 그에 관련된 것을 marker로 표시. 내위치 정보 표시
 *          참고 : 항상 내위치는 변함으로 db사용 필요없이 parsing한 결과를 마커로 표시해준다. 내위치 변경시에 마커또한 변경
 */
package mobile.proj.map;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import mobile.proj.join.R;
import mobile.proj.map.util.LocDto;
import mobile.proj.map.util.MyXMLParser;
import mobile.proj.map.util.NetworkManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

public class MapActivity extends FragmentActivity {
	private ArrayList<LocDto> resultList;
	private String resultXml;
	private GoogleMap mGoogleMap;
	Marker centerMarker;
	Marker myMarker;
	LocDto dto;
	LatLng lng;
	double latitude, longitude;
	LocationManager locManager;
	Geocoder mCoder;
	String str_lat;
	String str_lng;
	Geocoder coder;
	List<Address> addr;
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		
		resultList = new ArrayList<LocDto>();
//		레이아웃의 googleMap 준비
		mGoogleMap = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap(); //googleMap가져옴
		mCoder = new Geocoder(this);
		
		locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
//		Criteria criteria = new Criteria();
//        
//		criteria.setAccuracy(Criteria.NO_REQUIREMENT);
//	    criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
//	    criteria.setAltitudeRequired(false);
//	    criteria.setCostAllowed(false);
//	        
//	    String bestProvider = locManager.getBestProvider(criteria, true);
//	    locManager.requestLocationUpdates(bestProvider, 5000, 0, locListener);
		//실내에서 실행시 GPS 가 ON으로 되있는데 GPS가 bestProvider가 되면 검색이 안잡히는 경우가 있어서 일단 NETWORK 이용!!!
		locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, locListener); //위치정보 확인
    	boolean gpsOn = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    	boolean networkOn = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		String text = "GPS연결 : " + gpsOn + "\n NETWORK연결 : " + networkOn; 
		Toast.makeText(MapActivity.this, text , Toast.LENGTH_SHORT).show();
		

	        mGoogleMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

				@Override
				public void onInfoWindowClick(Marker marker) {
					centerMarker.showInfoWindow(); //infoWindow 로 마커번호, 위도, 경도 출력
				}
	        });
	    }
	    public void onClick(View v){
	    	switch(v.getId()){
	    		case R.id.btnMygps://내위치확인하기 버튼누를경우
//	    	        Criteria criteria = new Criteria();
//	    	        
//	    	        criteria.setAccuracy(Criteria.NO_REQUIREMENT);
//	    	        criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
//	    	        criteria.setAltitudeRequired(false);
//	    	        criteria.setCostAllowed(false);
//	    	        
//	    	        String bestProvider = locManager.getBestProvider(criteria, true);
//	    	    	locManager.requestLocationUpdates(bestProvider, 5000, 0, locListener);   
	    	    	locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, locListener);//위치정보확인 및 리스너
					
	    			
	    		break;
	    		case R.id.btnSearch: //주변 영화관 검색. daum 키워드 검색api 중 영화관으로 주변 반경에 있는 영화관정보를 가져온다. 
	    			
	    			String url = "https://apis.daum.net/local/v1/search/keyword.xml?apikey=1516115ec314a5b7745a9deb2e30158a74e3a58e&radius=10000&query=";
	    			url += encodeKorean("영화관") + "&location=" + str_lat + "," + str_lng;
	    			NetworkManager netManager = new NetworkManager(this,mXmlHandler,url); //NetworkManager 실행
	    			netManager.setDaemon(true);
	    			netManager.start();
	    			
	    			break;
	    	}
	    }
	
		Handler mXmlHandler = new Handler() {
			public void handleMessage(Message msg) { //영화관 검색 api 실행시
				resultXml = (String) msg.obj; //result 값 저장
				MyXMLParser parser = new MyXMLParser(); //파서 실행
				resultList = parser.parse(resultXml);
				for(LocDto dto : resultList) { //저장된 값을 바로 마커로 표시
    	    		MarkerOptions markerOptions = new MarkerOptions();
    	    		latitude = Double.parseDouble(dto.getLatitude());
    	    		longitude = Double.parseDouble(dto.getLongitude());
    	    		lng = new LatLng(latitude, longitude);
    	    		markerOptions.position(lng);
    	    		markerOptions.title(dto.getTitle());
    	    		markerOptions.snippet("주소: " + dto.getAddress() + "\n전화번호: " + dto.getPhone());
    	    		markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_logo));
    	    		
    	    		centerMarker = mGoogleMap.addMarker(markerOptions);
    	    		
				}
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
	    
	    LocationListener locListener = new LocationListener() {
	    	
//			위치 변경 시마다 호출
			@Override
			public void onLocationChanged(Location loc) {
				if(myMarker!=null) myMarker.remove();
				
				latitude = loc.getLatitude();	// 위도 확인
				longitude = loc.getLongitude();	// 경도 확인
				str_lat = Double.toString(latitude);
				str_lng = Double.toString(longitude);
				
				MarkerOptions marker = new MarkerOptions();
    			marker.position(new LatLng(latitude,longitude));
    			marker.title("내위치");
    			marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.my));
    			
    			myMarker = mGoogleMap.addMarker(marker);
				mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude), 15));
			}

//			현재 위치제공자가 사용이 불가해질 때 호출
			@Override
			public void onProviderDisabled(String provider) {
				Toast.makeText(MapActivity.this,"서비스 사용 불가", Toast.LENGTH_SHORT).show();
			}

//			현재 위치제공자가 사용가능해질 때 호출
			@Override
			public void onProviderEnabled(String provider) {
				Toast.makeText(MapActivity.this,"서비스 사용 가능", Toast.LENGTH_SHORT).show();
			}

//			위치제공자의 상태가 변할 때 호출
			@Override
			public void onStatusChanged(String provider, int status, Bundle extra) {
				String sStatus = "";
				switch(status) {
				case LocationProvider.OUT_OF_SERVICE:
					sStatus = "범위 벗어남";
					break;
				case LocationProvider.TEMPORARILY_UNAVAILABLE:
					sStatus = "일시적 불능";
					break;
				case LocationProvider.AVAILABLE:
					sStatus = "사용 가능";
					break;
				}
				Toast.makeText(MapActivity.this, sStatus, Toast.LENGTH_SHORT).show();	
			}
		};
		
}
