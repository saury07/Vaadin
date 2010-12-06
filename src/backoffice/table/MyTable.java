package backoffice.table;

import hibernate.HbnContainer.SessionManager;

import java.util.Iterator;
import java.util.Set;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public abstract class MyTable extends Table implements Refreshable{

	protected Layout pane;
	protected SessionManager session;
	protected final Button save = new Button("Edit");
	
	public MyTable(SessionManager session){
		this.session = session;
		setMultiSelect(true);
		//this.refresh();
		addListener(new ItemClickListener() {

			public void itemClick(ItemClickEvent event) {
				if(event.isDoubleClick()){
					setEditable(true);
					save.setCaption("Save");
				}					
			}
		});
	}
	
	public Layout getPane(){
		if(pane!=null){
			System.out.println("return from memory");
			return pane;
		}
		else{
		VerticalLayout vLay = new VerticalLayout();
		vLay.addComponent(this);
		HorizontalLayout hLay = new HorizontalLayout();
		
		
		final Button refresh = new Button("Refresh");
		final Button delete = new Button("Delete");
		
		save.addListener(new ClickListener() {
			
			public void buttonClick(ClickEvent event) {
				if(isEditable()){
					setEditable(false);
					save.setCaption("Edit");
				}
				else{
					setEditable(true);
					save.setCaption("Save");
				}
			}
		});
		
		refresh.addListener(new ClickListener() {
			
			public void buttonClick(ClickEvent event) {
				refresh();
			}
		});
		
		delete.addListener(new ClickListener() {
			
			public void buttonClick(ClickEvent event) {
				Set s = (Set)getValue();
				Iterator i = s.iterator();
				while(i.hasNext()){
					getContainerDataSource().removeItem(i.next());
				}
			}
		});
		
		hLay.addComponent(save);
		hLay.addComponent(refresh);
		hLay.addComponent(delete);
		hLay.setStyleName("red");

		vLay.addComponent(hLay);
		pane=vLay;
		return vLay;
		}
	}
	
}
