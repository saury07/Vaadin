package com.example.table;

import hibernate.HbnContainer;
import hibernate.HbnContainer.SessionManager;

import com.example.bean.Reservation;
import com.example.vaadin.MyFieldFactory;
import com.vaadin.data.Container;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.TableFieldFactory;

public class TableReservation extends Table {

	private SessionManager session;
	private MyFieldFactory fieldFactory;

	public TableReservation(SessionManager session) {
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
	
	public void refresh(){
		Container cont = new HbnContainer<Reservation>(Reservation.class, session);
		setImmediate(true);
		System.out.println(cont.getContainerPropertyIds());
		Object[] filter = {"user", "restaurant","couverts", "date"};
		setContainerDataSource(cont);
		setVisibleColumns(filter);
		setSelectable(true);
		setPageLength(5);
		//table.setMultiSelect(true);
		setTableFieldFactory(fieldFactory);

		setSortContainerPropertyId("restaurant");
		sort();
	}
	
}
