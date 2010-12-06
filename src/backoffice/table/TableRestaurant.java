package backoffice.table;

import java.io.Serializable;

import hibernate.HbnContainer;
import hibernate.HbnContainer.SessionManager;

import backoffice.bean.Restaurant;
import backoffice.bean.Ville;
import backoffice.vaadin.MyFieldFactory;

import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Table;
import com.vaadin.ui.Component.Event;

public class TableRestaurant extends MyTable {

	private MyFieldFactory fieldFactory = new MyFieldFactory();
	
	public TableRestaurant(SessionManager session){
		super(session);
		this.refresh();
	}
	
	public void refresh(){
		Container cont = new HbnContainer<Restaurant>(Restaurant.class, session);
		setImmediate(true);
		Object[] filter = {"nom","ville", "couverts"};
		setContainerDataSource(cont);
		setVisibleColumns(filter);
		setSelectable(true);
		setPageLength(5);
		//table.setMultiSelect(true);
		setTableFieldFactory(fieldFactory);

		setSortContainerPropertyId("nom");
		sort();
	}

	@Override
	protected String formatPropertyValue(Object rowId, Object colId,
			Property property) {
		Object p = property.getValue();
		//System.out.println("--------------------\n"+p.getClass()+", "+property.getType()+", "+property.getValue()+", "+colId);
		if(colId == "ville"){
			System.out.println("--------------------\n"+property.getValue());
			Ville v = (Ville) session.getSession().get(Ville.class, (Serializable)p);
			return v.getNom();
		}
		
		return super.formatPropertyValue(rowId, colId, property);
	}

	
	
}
