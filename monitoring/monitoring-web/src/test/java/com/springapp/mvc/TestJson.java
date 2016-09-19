package com.springapp.mvc;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

public class TestJson {

	@Test
	public void listJson() throws Exception{
		String json = "{\"name\":\"test\",\"ids\":[1,2,3]}";
		ObjectMapper object = new ObjectMapper();
		Map map  = object.readValue(json, Map.class);
		List list = (List)map.get("ids");
		for(int i=0;i<list.size();i++){
			System.out.println(list.get(i));
		}
	}
}
