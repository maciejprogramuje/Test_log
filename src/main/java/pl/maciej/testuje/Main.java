package pl.maciej.testuje;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.javascript.HtmlUnitContextFactory;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptEngine;
import com.gargoylesoftware.htmlunit.javascript.host.Element;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLButtonElement;
import net.sourceforge.htmlunit.corejs.javascript.Context;
import org.apache.commons.logging.LogFactory;
import org.apache.http.util.Asserts;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by m.szymczyk on 2017-01-04.
 */
public class Main {
    public static String MY_PASSWORD = "";
    public static final String MY_LOGIN = "";
    public static final String MAIN_WEB_PAGE_LINK = "https://recordings.integralnet.pl/admin-panel/login?lang=en";

    public static void main(String[] args) {
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);


        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        try {
            HtmlPage page = webClient.getPage(MAIN_WEB_PAGE_LINK);
            webClient.getOptions().setRedirectEnabled(true);
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.setAjaxController(new NicelyResynchronizingAjaxController());
            webClient.waitForBackgroundJavaScript(1000);

            //<input id="textfield-1010-inputEl" type="text" name="j_username" class="x-form-field x-form-required-field x-form-text" autocomplete="off" aria-invalid="false" data-errorqtip="">
            //<input id="login-form-password-inputEl" type="password" name="j_password" class="x-form-field x-form-required-field x-form-text" autocomplete="off" aria-invalid="false" data-errorqtip="">
            //<a class="x-btn x-unselectable x-box-item x-toolbar-item x-ltr x-btn-default-small x-noicon x-btn-noicon x-btn-default-small-noicon" role="button" hidefocus="on" unselectable="on" id="button-1012" style="width: 75px; right: auto; left: 180px; top: 0px; margin: 0px;" tabindex="0"><span id="button-1012-btnWrap" class="x-btn-wrap" unselectable="on"><span id="button-1012-btnEl" class="x-btn-button"><span id="button-1012-btnInnerEl" class="x-btn-inner x-btn-inner-center" unselectable="on">Submit</span><span role="img" id="button-1012-btnIconEl" class="x-btn-icon-el  " unselectable="on" style=""></span></span></span></a>

            DomElement loginElement = page.getElementById("textfield-1010-inputEl");
            loginElement.setAttribute("j_username", MY_LOGIN);

            DomElement passwordElement = page.getElementById("login-form-password-inputEl");
            passwordElement.setAttribute("j_password", MY_PASSWORD);

            webClient.waitForBackgroundJavaScript(1000);

            HtmlAnchor submitElement = page.getAnchors().get(0);
            HtmlPage clickedPage = submitElement.click();

            webClient.waitForBackgroundJavaScript(30000);

            System.out.println("=================================================");
            System.out.println("=================================================");
            System.out.println(clickedPage.asText());
            System.out.println("=================================================");

            /*HtmlForm form = (HtmlForm) page.getElementById("aspnetForm");

            HtmlInput login = form.getInputByName("ctl00$MainContent$SmartRecordLogin$UserName");
            login.setValueAttribute(MY_LOGIN);
            HtmlInput password = form.getInputByName("ctl00$MainContent$SmartRecordLogin$Password");
            password.setValueAttribute(MY_PASSWORD);
            HtmlAnchor logInAnchor = (HtmlAnchor) page.getElementById("ctl00_MainContent_SmartRecordLogin_LoginLinkButton");
            page = logInAnchor.click();

            DomElement recordingsTab = page.getElementById("ctl00_NavTabStrip_NavRecordingsTab");
            page = recordingsTab.click();

            DomElement filtersTab = page.getElementById("ctl00_MainContent_FiltersPanel1_FilterTitleLabel");
            page = filtersTab.click();

            DomElement dateTimeFilterTab = page.getElementById("__tab_ctl00_MainContent_FiltersPanel1_TabContainer1_DateTimeTabPanel");
            page = dateTimeFilterTab.click();

            HtmlSelect optionsDateTimeFilterTab = (HtmlSelect) page.getElementById("ctl00_MainContent_FiltersPanel1_TabContainer1_DateTimeTabPanel_DateOptions");
            optionsDateTimeFilterTab.setSelectedAttribute("Last30Days", false);
            optionsDateTimeFilterTab.setSelectedAttribute("Yesterday", true);*/

            //page = optionsDateTimeFilterTab.click();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
