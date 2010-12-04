package com.example.table;

import java.util.Date;

import hibernate.HbnContainer;
import hibernate.HbnContainer.SessionManager;

import com.example.bean.User;
import com.example.vaadin.MyFieldFactory;
import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Table;

public class TableUser extends Table {

	private SessionManager session;
	private MyFieldFactory fieldFactory = new MyFieldFactory();

	public TableUser(SessionManager session) {
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
		Container cont = new HbnContainer<User>(User.class, session);
		setImmediate(true);
		Object[] filter = {"genre","nom", "prenom", "telephone","date"};
		setContainerDataSource(cont);
		setVisibleColumns(filter);
		setColumnWidth("genre", 40);
		setColumnWidth("prenom", 200);
		setColumnWidth("nom", 200);
		setColumnWidth("telephone", 100);
		setColumnWidth("date", 100);
		setSelectable(true);
		setPageLength(15);
		//table.setMultiSelect(true);
		setTableFieldFactory(fieldFactory);

		setSortContainerPropertyId("nom");
		sort();
	}
	


	@Override
	protected String formatPropertyValue(Object rowId, Object colId,
			Property property) {
		Object v = property.getValue();
		if (v instanceof Date) {
			Date dateValue = (Date) v;
			int year = 1900+dateValue.getYear();
			return ""+dateValue.getDate()+"/"+dateValue.getMonth()+"/"+year;
		}
		return super.formatPropertyValue(rowId, colId, property);
	}

}
