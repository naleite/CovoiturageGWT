package fr.istic.gla.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.*;
import com.google.gwt.json.client.JSONArray;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

import java.util.Iterator;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class glagerseygwt implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		// Create a text
		final TextBox area = new TextBox();

		area.setValue("2");
		RootPanel.get().add(area);


		// Create a button
		com.google.gwt.user.client.ui.Button b = new Button();
		b.setText("test call json restful service");

		RootPanel.get().add(b);

		b.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, GWT
						.getHostPageBaseURL() + "rest/ev/");
				rb.setCallback(new RequestCallback() {

					public void onError(Request request, Throwable exception) {
						Window.alert(exception.getMessage());
					}

					public void onResponseReceived(Request request,
							Response response) {
						if (200 == response.getStatusCode()) {
                            JSONValue res=JSONParser.parseStrict(response.getText());
                            JSONArray array=res.isArray();

                            if(array !=null){
                                JSONObject jo= (JSONObject) array.get(Integer.parseInt(area.getValue()));
                                String s="ID:"+jo.get("id")+", Depart de: "+jo.get("localisation")+" a "+jo.get("destination")+",Conduicteur: "+jo.get("nom")+".";
                                Label label=new Label(s);
                                RootPanel.get().add(label);
                            }

							//Window.alert("get the book from :" );

						}
                        else {
                            Window.alert(response.getStatusCode()+"");
                        }
					}
				});
				try {
					rb.send();
				} catch (RequestException e) {
					e.printStackTrace();
				}

			}
		});

	}
}
