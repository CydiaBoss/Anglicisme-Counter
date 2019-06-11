package com.pc.ang;

import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class Checker {

	/**
	 * Checks if the word is an anglicismes
	 * 
	 * @param wrd
	 * The word
	 * 
	 * @return
	 * If anglicisme, TRUE
	 * 
	 * @throws IOException 
	 * Oof
	 * @throws MalformedURLException 
	 * Oof
	 * @throws FailingHttpStatusCodeException 
	 * Oof
	 * @throws InterruptedException 
	 */
	public static boolean check(String wrd) throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException {
		// Creates New Client
		WebClient c = new WebClient();
		c.getOptions().setUseInsecureSSL(true);
		c.getOptions().setCssEnabled(false);
		c.getOptions().setAppletEnabled(true);
		c.getOptions().setJavaScriptEnabled(true);
		c.getOptions().setActiveXNative(true);
		System.out.println("1");
		// Get Page
		HtmlPage pg = c.getPage("https://www.dictionnaire-academie.fr/");
		c.waitForBackgroundJavaScript(2000);
		// Get Form
		HtmlForm dic = pg.getFormByName("frm_search");
		// Get Inputs
		HtmlTextInput srch = dic.getInputByName("term");
		// Fill out
		srch.type(wrd);
		// Check Results
		c.waitForBackgroundJavaScript(2000);
		int test = pg.getByXPath("//div[contains(@class, 'noresult')]").size();
		pg.refresh();
		c.close();
		if(test >= 1)
			return true;
		
		/* Sample of Website
		 <form name="frm_search" id="frm_search" action="/search" method="post">
					<input name="term" class="hauteurAccueil" id="recherche" spellcheck="false" type="text" maxlength="255" placeholder="MOT À RECHERCHER" autocomplete="off">
					<input name="options" id="search_options" type="hidden" value="1">
					<div class="hauteurAccueil" id="btViderRecherche"></div>
					<div class="autocomplete-items positionAccueil" id="rechercheautocomplete-list" style="display: none;">
						<div id="contenantDeuxBoutons">
							<div class="btRechercheActive" id="motCommencant" data-value="1"><a href="#">Mots commençant par</a></div>
							<div class="btRechercheDisponible" id="motsProches" data-value="0"><a href="#">Mots proches</a></div>
						</div>
					<a href="https://www.dictionnaire-academie.fr/article/A9P3210-A" data-score="0.9998">Po, symb.</a><a href="https://www.dictionnaire-academie.fr/article/A9P3027" data-score="0.2941">pochade, n. f.</a><a href="https://www.dictionnaire-academie.fr/article/A9P3028" data-score="0.2941">pochage, n. m.</a><a href="https://www.dictionnaire-academie.fr/article/A9P3029" data-score="0.2941">pochard, -arde, n.</a><a href="https://www.dictionnaire-academie.fr/article/A9P3030" data-score="0.1971">pocharder (se), v. pron.</a><a href="https://www.dictionnaire-academie.fr/article/A8P1868" data-score="0.4098">poché, n. m.</a><a href="https://www.dictionnaire-academie.fr/article/A9P3031" data-score="0.4098">poche [I], n. f.</a><a href="https://www.dictionnaire-academie.fr/article/A9P3032" data-score="0.4098">poche [II], n. f.</a><a href="https://www.dictionnaire-academie.fr/article/A9P3033" data-score="0.3425">pocher, v. tr. et intr.</a><a href="https://www.dictionnaire-academie.fr/article/A9P3034" data-score="0.2577">pocheter, v. tr.</a><a href="https://www.dictionnaire-academie.fr/article/A9P3035" data-score="0.2294">pochetron, -onne, n.</a><a href="https://www.dictionnaire-academie.fr/article/A9P3036" data-score="0.1438">pochetronner (se), v. pron.</a><a href="https://www.dictionnaire-academie.fr/article/A9P3037" data-score="0.2577">pochette, n. f.</a><a href="https://www.dictionnaire-academie.fr/article/A9P3037-A" data-score="0.0991">pochette-surprise, n. f.</a><a href="https://www.dictionnaire-academie.fr/article/A9P3038" data-score="0.2941">pochoir, n. m.</a><a href="https://www.dictionnaire-academie.fr/article/A9P3039" data-score="0.3425">pochon [I], n. m.</a><a href="https://www.dictionnaire-academie.fr/article/A9P3040" data-score="0.3425">pochon [II], n. m.</a><a href="https://www.dictionnaire-academie.fr/article/A9P3041" data-score="0.2577">pochouse, n. f.</a></div>
					<div id="explicationRecherche">
						<p>Pour chercher un mot dans le dictionnaire, tapez simplement ses premières lettres. Utilisez le bouton « Mots proches » pour activer la correction orthographique et phonétique.</p>
					</div>
				</form>
		 */
		
		return false;
	}
}
