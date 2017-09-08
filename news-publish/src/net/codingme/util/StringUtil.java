package net.codingme.util;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * 
 * 
 * @author Niu on 2017年9月7日 下午8:39:29
 */
public class StringUtil {
	
	//判断一个字符串是否是数字
    public static boolean isNumeric(String str) {
        // 该正则表达式可以匹配所有的数字 包括负数
        Pattern pattern = Pattern.compile("-?[0-9]+\\.?[0-9]*");
        String bigStr;
        try {
            bigStr = new BigDecimal(str).toString();
        } catch (Exception e) {
            return false;//异常 说明包含非数字。
        }

        Matcher isNum = pattern.matcher(bigStr); // matcher是全匹配
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }
    
    public static void main(String[] args) {
		System.out.println(isNumeric("12313123"));
		System.out.println(isNumeric("12313d1123"));
		System.out.println(isNumeric("1231d3123"));
		System.out.println(isNumeric("123131123"));
		System.out.println(isNumeric("123131112@23"));
	}

}
