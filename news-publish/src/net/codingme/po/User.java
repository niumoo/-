package net.codingme.po; 
/** 
 * 用户表对应的实体类
 * 
 * @author Niu on 2017年9月4日 下午3:26:30
 */
public class User {
	//用户id
	private Integer userId;
	//用户类型1：管理员2普通用户
	private Integer type;
	//用户昵称
	private String userName;
	//用户邮箱 用于登陆
	private String userEmail;
	//用户密码 用于登陆
	private String password;
	//用户能管理的信息栏目ID,多个用‘,’号分割
	private String manegeCategory;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getManegeCategory() {
		return manegeCategory;
	}
	public void setManegeCategory(String manegeCategory) {
		this.manegeCategory = manegeCategory;
	}
	public User(Integer userId, Integer type, String userName, String userEmail, String password,
			String manegeCategory) {
		super();
		this.userId = userId;
		this.type = type;
		this.userName = userName;
		this.userEmail = userEmail;
		this.password = password;
		this.manegeCategory = manegeCategory;
	}
	
	public User(){
		
	}

}
