package backoffice.table;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import hibernate.HbnContainer;
import hibernate.HbnContainer.SessionManager;

import backoffice.bean.User;
import backoffice.vaadin.MyFieldFactory;

import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

public class TableUser extends MyTable {

	
	private MyFieldFactory fieldFactory = new MyFieldFactory();

	
	public TableUser(SessionManager session) {
		super(session);
		this.refresh();
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
