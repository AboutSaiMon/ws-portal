/*
 * Copyright © 2010
 *
 * This file is part of "WS Portal" web application.
 *
 * WS Portal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * WS Portal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with WS Portal.  If not, see <http://www.gnu.org/licenses/>.
 */
package it.unical.inf.wsportal.client.callback;

import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.google.gwt.http.client.RequestException;
import it.unical.inf.wsportal.client.util.AsynchRequest;
import it.unical.inf.wsportal.client.util.Toolkit;

/**
 *
 * @author Simone Spaccarotella {spa.simone@gmail.com}, Carmine Dodaro {carminedodaro@gmail.com}
 */
public class ShowWsdlDialog extends Callback {

    @Override
    public void handleEvent(MessageBoxEvent event) {
        Button button = event.getButtonClicked();
        // if the user clicked the "Ok" button then
        if (button.getText().equals("Ok")) {
            // get the value of the text field and check if it's valid
            String url = event.getValue();
            // if it is
            if (Toolkit.isValidUrl(url)) {
                // perform an asynchronous call to the server
                try {
                    String param = "url=" + url;
                    AsynchRequest request = new AsynchRequest(AsynchRequest.POST, "../wslib-generator", param);
                    MessageBox box = MessageBox.wait("Progress", "Generating the library, please wait...", "Loading...");
                    request.setCallback(new WsLibGenerator(box));
                    request.send();
                } catch (RequestException ex) {
                    Toolkit.showException(ex);
                }
            } else {
                // else warn the user, the URL is not valid
                Toolkit.showWarning("Type a valid URL");
            }
        }
    }
}
