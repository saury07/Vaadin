package backoffice.table;

import hibernate.HbnContainer;
import hibernate.HbnContainer.SessionManager;

import backoffice.bean.Ville;
import backoffice.vaadin.MyFieldFactory;

import com.vaadin.data.Container;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Table;

public class TableCity extends MyTable {

	private MyFieldFactory fieldFactory = new MyFieldFactory();

	public TableCity(SessionManager session) {
		super(session);
		this.refresh();
	}

	public void refresh(){
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
