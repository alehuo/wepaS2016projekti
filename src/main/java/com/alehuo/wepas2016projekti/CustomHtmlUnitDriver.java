/*
 * Copyright (C) 2016 alehuo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.alehuo.wepas2016projekti;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.IncorrectnessListener;
import com.gargoylesoftware.htmlunit.InteractivePage;
import com.gargoylesoftware.htmlunit.ScriptException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptErrorListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.CSSParseException;
import org.w3c.css.sac.ErrorHandler;

/**
 * Custom HtmlUnitDriver
 *
 * @author alehuo
 */
public class CustomHtmlUnitDriver extends HtmlUnitDriver {

    static {
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");

        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);
    }

    /**
     *
     * @param bV
     * @param status
     */
    public CustomHtmlUnitDriver(BrowserVersion bV, boolean status) {
        super(bV, status);
    }

    @Override
    protected WebClient modifyWebClient(WebClient client) {
        WebClient modifiedClient = super.modifyWebClient(client);
        modifiedClient.getOptions().setThrowExceptionOnScriptError(false); //Skript error reporting pois päältä ettei esim. jQuery heitä virhettä
        modifiedClient.setIncorrectnessListener(new IncorrectnessListener() {
            @Override
            public void notify(String string, Object o) {

            }
        });
        modifiedClient.setJavaScriptErrorListener(new JavaScriptErrorListener() {
            @Override
            public void scriptException(InteractivePage ip, ScriptException se) {

            }

            @Override
            public void timeoutError(InteractivePage ip, long l, long l1) {

            }

            @Override
            public void malformedScriptURL(InteractivePage ip, String string, MalformedURLException murle) {

            }

            @Override
            public void loadScriptError(InteractivePage ip, URL url, Exception excptn) {

            }
        });
        modifiedClient.setCssErrorHandler(new ErrorHandler() {
            @Override
            public void warning(CSSParseException csspe) throws CSSException {

            }

            @Override
            public void error(CSSParseException csspe) throws CSSException {

            }

            @Override
            public void fatalError(CSSParseException csspe) throws CSSException {

            }
        });
        return modifiedClient;
    }

}
