package com.example.vaadin;

import hibernate.HbnContainer;
import hibernate.UserSession;
import hibernate.HbnContainer.SessionManager;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;

import com.example.bean.Reservation;
import com.example.bean.Restaurant;
import com.example.bean.User;
import com.example.table.TableCity;
import com.example.table.TableReservation;
import com.example.table.TableRestaurant;
import com.example.table.TableUser;
import com.mysql.jdbc.Driver;
import com.mysql.jdbc.Statement;

import com.vaadin.Application;
import com.vaadin.addon.sqlcontainer.SQLContainer;
import com.vaadin.addon.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.addon.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.addon.sqlcontainer.query.TableQuery;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.QueryContainer;
import com.vaadin.service.ApplicationContext.TransactionListener;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component.Event;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.data.Property;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.event.LayoutEvents;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.MouseEvents;

public class VaadinApplication extends Application {

	private MyFieldFactory fieldFactory;
	private UserSession session;

	private void init_good(){
		this.fieldFactory = new MyFieldFactory();
		session = new UserSession(this);
		Window mainWindow = new Window("Vaadin users");
		VerticalLayout mainLay = new VerticalLayout();
		mainWindow.addComponent(mainLay);
		mainWindow.setScrollable(true);

		Label titre = new Label("RESTaurant");
		titre.setStyleName("h1");		
		final TableUser table = new TableUser(session);


		table.refresh();

		final TableReservation reservations = new TableReservation(session);
		
		final Button edit = new Button("Editer");
		edit.addListener(new ClickListener() {

			public void buttonClick(ClickEvent event) {
				if(table.isEditable()){
					table.setEditable(false);
					edit.setCaption("Editer");
				}
				else{
					table.setEditable(true);
					edit.setCaption("Sauver");
				}
			}
		});

		final Formulaire form = new Formulaire(User.class, session);
		//form.setCaption("Ajouter utilisateur");
		final User user = new User();		

		
		
		/*Button commit = new Button("Sauver",form,"commit");
		commit.addListener(new ClickListener() {

			public void buttonClick(ClickEvent event) {
				user.setDate(new Date());
				addUser(user);
				table.refresh();
				form.getField("nom").setValue("");
				form.getField("genre").setValue("M.");
				form.getField("prenom").setValue("");
				form.getField("telephone").setValue("");
				//form.setVisibleItemProperties(ordre);
			}
		});
		*/

		Button refresh = new Button("Refresh");
		refresh.addListener(new ClickListener() {

			public void buttonClick(ClickEvent event) {
				table.refresh();
			}
		});

		Button delete = new Button("Supprimer");
		delete.addListener(new ClickListener() {

			public void buttonClick(ClickEvent event) {
//				Set s = (Set)table.getValue();
//				Iterator i = s.iterator();
//				while(i.hasNext()){
//					table.getContainerDataSource().removeItem(i.next());
//				}
				table.getContainerDataSource().removeItem(table.getValue());
				table.refresh();
			}
		});

		form.setStyleName("form");	
		mainLay.addComponent(titre);
		mainLay.setComponentAlignment(titre, Alignment.MIDDLE_CENTER);
		mainLay.addComponent(table);
		mainLay.setComponentAlignment(table, Alignment.MIDDLE_CENTER);
		HorizontalLayout h_lay = new HorizontalLayout();
		TableCity t = new TableCity(session);
		h_lay.addComponent(t);
		TableRestaurant tr = new TableRestaurant(session);
		h_lay.addComponent(tr);
		h_lay.addComponent(reservations);
		mainLay.addComponent(h_lay);
		mainLay.setComponentAlignment(h_lay, Alignment.MIDDLE_CENTER);
		mainLay.addComponent(edit);
		mainLay.setComponentAlignment(edit, Alignment.MIDDLE_CENTER);
		mainLay.addComponent(refresh);
		mainLay.setComponentAlignment(refresh, Alignment.MIDDLE_CENTER);
		mainLay.addComponent(delete);
		mainLay.setComponentAlignment(delete, Alignment.MIDDLE_CENTER);
		mainLay.addComponent(form);
		//mainLay.setComponentAlignment(form, Alignment.MIDDLE_CENTER);
	//	mainLay.addComponent(commit);
	//	mainLay.setComponentAlignment(commit, Alignment.MIDDLE_CENTER);
		setTheme("mytheme");
		setMainWindow(mainWindow);
	}

	public void addUser(User user){
		session.getSession().merge(user);
	}
	
	public void addReservation(Reservation res){
		session.getSession().merge(res);
	}
	
	public UserSession getSession() {
		return session;
	}

	@Override
	public void init(){

		init_good();
		session.addTransactionListener();

	}


}
