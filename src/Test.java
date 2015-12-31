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
	  // ����һ��ץȡ��վ��������ã��������롢ץȡ��������Դ�����
    private Site site = Site.me().setRetryTimes(3).setSleepTime(10000)
    	//.setHttpProxy(httpProxy)
    .addCookie("ASPSESSIONIDAABBABTS", "PAONOBJBHABHJMDMGMHMNNEG");

    @Override
    // process�Ƕ��������߼��ĺ��Ľӿڣ��������д��ȡ�߼�
    public void process(Page page)  {
        // ���ֶ���������γ�ȡҳ����Ϣ������������
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
        
        
        // ����������ҳ�淢�ֺ�����url��ַ��ץȡ
       // page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/\\w+/\\w+)").all());
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {

        Spider.create(new Test())
                //��"https://github.com/code4craft"��ʼץ
                .addUrl("http://qzsmgc.com/list/cun_hu_24_1160__1_")
                //����5���߳�ץȡ
                .thread(10)
                //��������
                .addPipeline(new JsonFilePipeline("D:\\aaaa\\"))
                .run();
    }
}

