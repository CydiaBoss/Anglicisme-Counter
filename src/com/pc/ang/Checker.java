package com.pc.ang;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

import org.apache.commons.logging.LogFactory;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.IncorrectnessListener;
import com.gargoylesoftware.htmlunit.ScriptException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HTMLParserListener;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptErrorListener;

public class Checker {

	public WebClient c;
	public HtmlPage pg;
	
	/**
	 * Create Checker
	 * @throws IOException 
	 * Failed to Access https://www.dictionnaire-academie.fr/
	 * @throws MalformedURLException 
	 * Invalid URL
	 * @throws FailingHttpStatusCodeException 
	 * https://www.dictionnaire-academie.fr/ is down
	 */
	public Checker() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		// Remove Error Msg
		LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF); 
		java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);
		// Create Objs
		c = new WebClient();
		// Settings
		c.getOptions().setUseInsecureSSL(true);
		c.getOptions().setCssEnabled(false);
		c.getOptions().setAppletEnabled(true);
		c.getOptions().setJavaScriptEnabled(true);
		c.getOptions().setActiveXNative(true);
		c.getOptions().setPrintContentOnFailingStatusCode(false);
		// Error Removal
		c.setIncorrectnessListener(new IncorrectnessListener() {
			
		    @Override
		    public void notify(String arg0, Object arg1) {
		    	
		    }
		});
		c.setJavaScriptErrorListener(new JavaScriptErrorListener() {

		    @Override
		    public void timeoutError(HtmlPage arg0, long arg1, long arg2) {
		        // TODO Auto-generated method stub

		    }

		    @Override
		    public void scriptException(HtmlPage arg0, ScriptException arg1) {
		        // TODO Auto-generated method stub

		    }

		    @Override
		    public void malformedScriptURL(HtmlPage arg0, String arg1, MalformedURLException arg2) {
		        // TODO Auto-generated method stub

		    }

		    @Override
		    public void loadScriptError(HtmlPage arg0, URL arg1, Exception arg2) {
		        // TODO Auto-generated method stub

		    }

			@Override
			public void warn(String message, String sourceName, int line, String lineSource, int lineOffset) {
				// TODO Auto-generated method stub
				
			}
		});
		c.setHTMLParserListener(new HTMLParserListener() {

			@Override
			public void error(String message, URL url, String html, int line, int column, String key) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void warning(String message, URL url, String html, int line, int column, String key) {
				// TODO Auto-generated method stub
				
			}
		});

		c.getOptions().setThrowExceptionOnFailingStatusCode(false);
		c.getOptions().setThrowExceptionOnScriptError(false);
		// Get Page
		pg = c.getPage("https://www.dictionnaire-academie.fr/");
		c.waitForBackgroundJavaScript(500);
	}
	
	/**
	 * Checks if the word is an anglicismes
	 * 
	 * @param wrd
	 * The word
	 * 
	 * @return
	 * If it is an anglicisme, TRUE
	 * Else, FALSE
	 * 
	 * @throws IOException 
	 * 
	 */
	public boolean check(String wrd) throws IOException{
		// Inform
		System.out.println("\"" + wrd + "\" is being Tested");
		// Get Form
		HtmlForm dic = pg.getFormByName("frm_search");
		// Get Inputs
		HtmlTextInput srch = dic.getInputByName("term");
		// Fill out
		srch.type(wrd);
		// Check Results
		c.waitForBackgroundJavaScript(2000);
		// Test
		int test = pg.getByXPath("//div[contains(@class, 'noresult')]").size();
		// Refresh
		refresh();
		// Passed
		if(test >= 1)
			return true;
		// Failed
		return false;
	}
	
	public void refresh() {
		// Get Page
		try {
			pg = c.getPage("https://www.dictionnaire-academie.fr/");
		} catch (FailingHttpStatusCodeException | IOException e) {}
		c.waitForBackgroundJavaScript(500);
	}
	
	/**
	 * Close Browser
	 */
	public void close() {
		c.close();
	}
}
