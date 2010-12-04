package com.example.table;

import hibernate.HbnContainer;
import hibernate.HbnContainer.SessionManager;

import com.example.bean.Restaurant;
import com.example.vaadin.MyFieldFactory;
import com.vaadin.data.Container;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Table;
import com.vaadin.ui.Component.Event;

public class TableRestaurant extends Table {

	private SessionManager session;
	private MyFieldFactory fieldFactory = new MyFieldFactory();
	
	public TableRestaurant(SessionManager session){
		super();
		this.session = session;
		this.refresh();
		addListener(new ItemClickListener() {
			
			public void itemClick(ItemClickEvent event) {
				if(event.isDoubleClick()){
					setEditable(true);
				}					
			}
		});
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

	
}
