package backoffice.table;

import java.io.Serializable;

import hibernate.HbnContainer;
import hibernate.HbnContainer.SessionManager;

import backoffice.bean.Reservation;
import backoffice.bean.Restaurant;
import backoffice.bean.User;
import backoffice.vaadin.MyFieldFactory;

import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.TableFieldFactory;

public class TableReservation extends MyTable {

	private MyFieldFactory fieldFactory;

	public TableReservation(SessionManager session) {
		super(session);
		this.refresh();
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

	@Override
	protected String formatPropertyValue(Object rowId, Object colId,
			Property property) {
		Object p = property.getValue();
		if(colId == "restaurant"){
			Restaurant r = (Restaurant)session.getSession().get(Restaurant.class, (Serializable)p);
			return r.getNom();
		}
		else if(colId == "user"){
			User u = (User)session.getSession().get(User.class, (Serializable)p);
			return u.getNom();
		}
		
		return super.formatPropertyValue(rowId, colId, property);
	}

	
	
}
