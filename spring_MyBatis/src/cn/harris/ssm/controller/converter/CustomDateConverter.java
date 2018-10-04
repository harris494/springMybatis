package cn.harris.ssm.controller.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

/**
 * 日期转化器
 * @author harri
 *
 */
public class CustomDateConverter implements Converter<String, Date>{

	@Override
	public Date convert(String source) {
		// TODO Auto-generated method stub
		
		//日期串转化成日期类型(yyyy-MM-dd HH:mm:ss)
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return simpleDateFormat.parse(source);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
