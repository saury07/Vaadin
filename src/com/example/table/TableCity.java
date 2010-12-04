package com.example.table;

import hibernate.HbnContainer;
import hibernate.HbnContainer.SessionManager;

import com.example.bean.Ville;
import com.example.vaadin.MyFieldFactory;
import com.vaadin.data.Container;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Table;

public class TableCity extends Table {

	private SessionManager session;
	private MyFieldFactory fieldFactory = new MyFieldFactory();

	public TableCity(SessionManager session) {
		super();
		this.session = session;
		refresh();
		addListener(new ItemClickListener() {

			public void itemClick(ItemClickEvent event) {
				if(event.isDoubleClick()){
					setEditable(true);
				}					
			}
		});
	}

	private void refresh(){
		Container cont = new HbnContainer<Ville>(Ville.class, session);
		setImmediate(true);
		Object[] filter = {"nom","code_postal"};
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
