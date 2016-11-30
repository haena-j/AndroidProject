/* 작성일 : 2014년12월14일
 * 작성자 : 정혜윤
 * 클래스 설명 : 무비함에 필요한 parser
 */
package mobile.proj.search.util;

import java.io.StringReader;
import java.util.ArrayList;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.text.Html;
import android.util.Log;

public class MyXMLParser {

	public final static String TAG = "MyXMLParser";
	
	private XmlPullParser mParser = null;

	public MyXMLParser() {
		try {

			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			mParser = factory.newPullParser();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<ItemDto> parse(String xml) {

		Log.i("imageXmlTest",xml);
		ArrayList<ItemDto> resultList = new ArrayList<ItemDto>();

		try {
//			String으로 전달 받은 xml 을 XmlPullParser 에 설정
			mParser.setInput(new StringReader(xml));
			
			int tagType = 0;
			ItemDto tmpDto = new ItemDto();
//			문서의 마지막이 될 때까지 읽어들이는 부분의 이벤트를 구분하여 반복 수행
			for (int eventType = mParser.getEventType();  eventType != XmlPullParser.END_DOCUMENT; eventType = mParser.next()) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					Log.i(TAG, "start document");
					break;
				case XmlPullParser.END_DOCUMENT:
					Log.i(TAG, "end document");
					break;
				case XmlPullParser.START_TAG:
					Log.i(TAG, "read start tag: " + mParser.getName());
					
					if(mParser.getName().equals("title"))	tagType = 1;
					else if(mParser.getName().equals("subtitle"))	tagType = 2;
					else if(mParser.getName().equals("pubDate"))	tagType = 3;
					else if (mParser.getName().equals("director")) tagType = 4;
					else if(mParser.getName().equals("actor")) tagType = 5;
					else if(mParser.getName().equals("userRating")) tagType = 6;
					else if(mParser.getName().equals("image")) tagType = 7;
					
					break;
				case XmlPullParser.END_TAG:
					Log.i(TAG, "read end tag: " + mParser.getName());
					
					if(mParser.getName().equals("item")){
						resultList.add(tmpDto);
						tmpDto = new ItemDto();
					}
					
					break;
				case XmlPullParser.TEXT:
					Log.i(TAG, "read text: " + mParser.getText());
					
					if(tagType == 1) tmpDto.setTitle(Html.fromHtml(mParser.getText()).toString());


					else if(tagType == 2)tmpDto.setSubtitle(mParser.getText());

					else if (tagType == 3)tmpDto.setPubDate(mParser.getText());

					else if (tagType == 4)tmpDto.setDirector(mParser.getText());
	
					else if (tagType == 5) tmpDto.setActor(mParser.getText());
					
					else if (tagType == 6) tmpDto.setUserRating(mParser.getText());
	
					else if (tagType == 7) tmpDto.setImage(mParser.getText());

					tagType = 0;
					break;
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultList;
	}

}
