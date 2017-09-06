package net.codingme.po; 
/** 
 * 信息栏目对应的实体类
 * 
 * @author Niu on 2017年9月4日 下午3:34:00
 */
public class Category {
	//信息栏目ID
	private Integer cId;
	//信息栏目名称
	private String cName;
	
	public Integer getcId() {
		return cId;
	}
	public void setcId(Integer cId) {
		this.cId = cId;
	}
	public String getcName() {
		return cName;
	}
	public void setcName(String cName) {
		this.cName = cName;
	}
	public Category(Integer cId, String cName) {
		super();
		this.cId = cId;
		this.cName = cName;
	}
	public Category(){
		
	}
	
	

}
