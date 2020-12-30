package com.sbs.example.ucong;

import com.sbs.example.ucong.util.Util;

public class Main {
	public static void main(String[] args) {
		testApi();
		//new App().run();
	}

	private static void testApi() {
		String url = "https://disqus.com/api/3.0/forums/listThreads.json";
		String rs = Util.callApi(url,"api_key=AYoqRgidP7DfQxNRxRiJ11Ciqq3iL3GdXUqKdjvsZdZzbLrADjPoBRmOWrYB0Wrt","forum=cong-ssg","thread:ident=article_detail_1.html");
		
		System.out.println(rs);
	}
}
