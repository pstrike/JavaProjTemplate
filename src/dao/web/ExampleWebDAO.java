package dao.web;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import utility.log.LoggerManager;
import model.Example;

public class ExampleWebDAO
{
	private static final String TTJJURL = "http://fund.eastmoney.com/[STOCKCODE].html";
	private static final String STOCK_CODE_IND = "[STOCKCODE]";
	private static final String ERR_STRING = "---";
	
	public Example getExampleData(String exampleId)
	{
		Example result = null;
		
		try
		{
			String stockUrl = generateStockURL(exampleId);
			
			Document doc =  Jsoup.connect(stockUrl).get();
            
            Element estimatedValueDiv = doc.getElementById("statuspzgz");
            Element estimatedValueSpan = estimatedValueDiv.child(0);	
            
            Element valueDiv = doc.getElementById("jzTab0");
            Element valueUl = valueDiv.child(1);
            Element valueSpan = valueUl.child(1);
            
            if(!estimatedValueSpan.text().equals(ERR_STRING))
            {
            	result = new Example();
            	result.setAbc(estimatedValueSpan.text());
            }
		}
        catch (Exception ex)  
        {
        	LoggerManager.getInstance().getLogger().error("Get Fund From TTJJ Exception: "+exampleId);
        	LoggerManager.getInstance().getLogger().error(ex);
        	ex.printStackTrace();
        }
		
		return result;
	}
	
	private String generateStockURL(String stockId)
	{
		return TTJJURL.replace(STOCK_CODE_IND, stockId);
	}
}
