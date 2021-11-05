package com.hpw.demo.reptile;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class Reptile {
    public static void main(String[] args) throws IOException {

        //连接到imooc
        Document doc = Jsoup.connect("http://www.imooc.com").get();
        //寻找a标签中的href
        Elements links = doc.select("a[href]");

        print("\nLinks:");
        for (Element link : links) {
            //获取href
            String url = link.attr("abs:href");
            //获取text
            String text = trim(link.text(), 35);
            if(url != null && url != "" && text != null && text != "") {
                if(url.contains("/learn/")) {
                    print(" * a: <%s>  (%s)", url, text);
                    //获取到某课程的地址
                    Document document = Jsoup.connect(url).get();
                    Elements link1 = document.select("a[href]");
                    //循环获取该课程的章节目录
                    for(Element lk : link1) {
                        String url1 = lk.attr("abs:href");
                        String text1 = trim(lk.text(), 35);
                        if(url1 != null && url1 != "" && text1 != null && text1 !="") {
                            if(url1.contains("video") || url1.contains("code/")){
                                print("        * a: <%s> (%s)", url1, text1);
                            }
                        }
                    }
                }
            }
        }
    }

    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }
}
