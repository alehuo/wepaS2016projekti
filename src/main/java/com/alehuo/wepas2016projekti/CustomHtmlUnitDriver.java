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
import com.gargoylesoftware.htmlunit.WebClient;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 * Custom HtmlUnitDriver
 *
 * @author alehuo
 */
public class CustomHtmlUnitDriver extends HtmlUnitDriver {

    public CustomHtmlUnitDriver(BrowserVersion bV) {
        super(bV);
    }

    @Override
    protected WebClient modifyWebClient(WebClient client) {
        WebClient modifiedClient = super.modifyWebClient(client);
        modifiedClient.getOptions().setThrowExceptionOnScriptError(false); //Skript error reporting pois päältä ettei esim. jQuery heitä virhettä
        return modifiedClient;
    }

}
