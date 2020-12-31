package com.sbs.example.ucong.test;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbs.example.ucong.apidto.DisqusApiDataListThread;
import com.sbs.example.ucong.container.Container;
import com.sbs.example.ucong.util.Util;

public class TestRunner {
	public static class TestDAtaType1{
		@JsonIgnoreProperties(ignoreUnknown = true)
		
		public int age;
		public String name;
		public int height;
	}
	public void run() {
		// testApi();
		//testApi2();
		testApi3();
		// testJackson();
		// testJackson2();
		// testJackson3();
		//testJackson4();
		//testJackson5();
	}


	private void testApi() {
		String url = "https://disqus.com/api/3.0/forums/listThreads.json";
		String rs = Util.callApi(url, "api_key="+ Container.config.getDisqusApiKey(),
				"forum=cong-ssg", "thread:ident=article_detail_1.html");

		System.out.println(rs);
	}
	
	private void testApi2() {
		String url = "https://disqus.com/api/3.0/forums/listThreads.json";
		Map<String, Object> rs = Util.callApiResponseToMap(url,  "api_key="+ Container.config.getDisqusApiKey(),
				"forum=cong-ssg", "thread:ident=article_detail_1.html");
		List<Map<String, Object>> response = (List<Map<String, Object>>) rs.get("response");
		System.out.println(rs.get("code"));
		Map<String, Object> thread = response.get(0);
		System.out.println((int) thread.get("likes"));
	}
	
	private void testApi3() {
		String url = "https://disqus.com/api/3.0/forums/listThreads.json";
		DisqusApiDataListThread rs = (DisqusApiDataListThread)Util.callApiResponseTo(DisqusApiDataListThread.class,url,  "api_key="+ Container.config.getDisqusApiKey(),
				"forum=cong-ssg", "thread:ident=article_detail_1.html");
	
		System.out.println(rs.response.get(0).likes + rs.response.get(0).posts);
	}

	private void testJackson() {
		String jsonString = "{\"age\":22, \"name\":\"홍길동\"}";

		ObjectMapper ob = new ObjectMapper();
		Map rs = null;

		try {
			rs = ob.readValue(jsonString, Map.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return;
		}

		System.out.println(rs);
		System.out.println(rs.get("age"));

	}

	private void testJackson2() {
		String jsonString = "1";

		ObjectMapper ob = new ObjectMapper();
		Integer rs = null;

		try {
			rs = ob.readValue(jsonString, Integer.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return;
		}

		System.out.println(rs);
	}

	private void testJackson3() {
		String jsonString = "[1,2,3]";

		ObjectMapper ob = new ObjectMapper();
		List<Integer> rs = null;

		try {
			rs = ob.readValue(jsonString, List.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return;
		}

		System.out.println(rs);
		System.out.println(rs.get(1));
	}

	private void testJackson4() {
		String jsonString = "[{\"age\":22, \"name\":\"홍길동\"},{\"age\":25, \"name\":\"홍길순\"},{\"age\":20, \"name\":\"홍길삼\"}]";

		ObjectMapper ob = new ObjectMapper();
		List<Map<String,Object>> rs = null;

		try {
			rs = ob.readValue(jsonString, List.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return;
		}

		System.out.println(rs);
		System.out.println(rs.get(1));
		System.out.println(rs.get(1).get("age"));
	}
	
	private void testJackson5() {
		String jsonString = "[{\"age\":22, \"name\":\"홍길동\", \"height\":178},{\"age\":23, \"name\":\"홍길순\", \"height\":168},{\"age\":24, \"name\":\"임꺽정\"}]";

		ObjectMapper ob = new ObjectMapper();
		List<TestDAtaType1> rs = null;
		
		
		try {
			rs = ob.readValue(jsonString, new TypeReference<List<TestDAtaType1>>(){});
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return;
		}
		
		System.out.println(rs.get(0).height + rs.get(2).height);
	}
}
