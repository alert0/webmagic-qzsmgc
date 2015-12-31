import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;


public class Test implements PageProcessor  {

	
//	Cookie Name	Value
//	ASPSESSIONIDAABBABTS	PAONOBJBHABHJMDMGMHMNNEG
	HttpHost  httpProxy =new HttpHost("223", 808);
	  // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(10000)
    	//.setHttpProxy(httpProxy)
    .addCookie("ASPSESSIONIDAABBABTS", "PAONOBJBHABHJMDMGMHMNNEG");

    @Override
    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    public void process(Page page)  {
        // 部分二：定义如何抽取页面信息，并保存下来
//        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
//        page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
//        if (page.getResultItems().get("name") == null) {
//            //skip this page
//            page.setSkip(true);
//        }
//        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));

        if(page.getResultItems().getRequest().getUrl().indexOf("show_nr") > -1 ) {
        	 page.putField("test",page.getHtml().xpath("//BODY").toString() );
        	 System.out.println(page.getHtml().xpath("//BODY").toString() );
        }
    	
    	
        List<String> tempurls =   page.getHtml().xpath("//a[@class='list']/@onclick").all();
        
        List<String > urls = new ArrayList<String>();
        
        for(String temp : tempurls){
        	String[] datas= temp.split("','");
        	String p = datas[3].substring(0, datas[3].indexOf("^^"));
        	System.out.println("http://qzsmgc.com/list/cun_hu_show_nr_" + p ) ;
        	urls.add("http://qzsmgc.com/list/cun_hu_show_nr_" + p) ;
        }
        
        page.addTargetRequests(urls);
        
      //  System.out.print(url);
        
        
        // 部分三：从页面发现后续的url地址来抓取
       // page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/\\w+/\\w+)").all());
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {

        Spider.create(new Test())
                //从"https://github.com/code4craft"开始抓
                .addUrl("http://qzsmgc.com/list/cun_hu_24_1160__1_")
                //开启5个线程抓取
                .thread(10)
                //启动爬虫
                .addPipeline(new JsonFilePipeline("D:\\aaaa\\"))
                .run();
    }
}

