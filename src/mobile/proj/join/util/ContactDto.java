/* 작성일 : 2014년12월14일
 * 작성자 : 정혜윤
 * 클래스 설명 : 회원의 정보 Dto
 */

package mobile.proj.join.util;

public class ContactDto {

	private String id;
	private String pw;
	private String phone;
	private String email;
	public ContactDto(String id){
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}


	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ContactDto() {
		super();
		// TODO Auto-generated constructor stub
	}



	public String toString() {
		String text ="";
		return text;
		
	}
}
