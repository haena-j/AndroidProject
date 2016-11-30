/* 작성일 : 2014년12월14일
 * 작성자 : 정혜윤
 * 클래스 설명 : 영화검색부분에 사용되는 NetworkManager
 */
package mobile.proj.search.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class NetworkManager extends Thread {
	
	private Context mContext;
	private String mServerUrl;
	private String mResult;
	private Handler mHandler;

	public NetworkManager(Context context, Handler handler, String serverUrl) {
		mContext = context;
		mHandler = handler;
		mServerUrl = serverUrl;
		mResult = null;
	}

	public void run() {
		StringBuilder receivedData = new StringBuilder(); 
		try {
			URL url = new URL(mServerUrl);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			if (conn != null) {
				conn.setConnectTimeout(3000);
				conn.setUseCaches(false);
				if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
					BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					while(true) {
						String line = br.readLine();
						if (line == null) break;
						receivedData.append(line + '\n'); 
					}
					br.close();
					mResult = receivedData.toString();
				}
				conn.disconnect();
			}
		} catch (SocketTimeoutException e) {
//			server 연결 시간(10000)을 초과하였을 경우
		//	Toast.makeText(mContext, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Message msg = mHandler.obtainMessage();
		msg.obj = mResult;
		mHandler.sendMessage(msg);
	}
}