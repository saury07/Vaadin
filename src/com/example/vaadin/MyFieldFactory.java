package com.example.vaadin;

import com.vaadin.data.Container;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.Select;

public class MyFieldFactory extends DefaultFieldFactory {

	
	
	@Override
	public Field createField(Container container, Object itemId,
			Object propertyId, Component uiContext) {
		
		 final Field f = super.createField(container, itemId, propertyId, uiContext);
		 
	     if (propertyId.equals("date")) {
             ((DateField) f).setResolution(DateField.RESOLUTION_DAY);
         }
	     else if(propertyId.equals("genre")){
	    	 Select select = new Select();
	    	 select.addItem("M.");
	    	 select.addItem("Mme.");
	    	 select.addItem("Melle.");
	    	 return select;
	     }
	     return f;
	}
	
	

	
	
	
}
