package backoffice.vaadin;

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

import backoffice.bean.Reservation;
import backoffice.bean.Restaurant;
import backoffice.bean.User;
import backoffice.table.TableCity;
import backoffice.table.TableReservation;
import backoffice.table.TableRestaurant;
import backoffice.table.TableUser;

import client.Login;

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
	private Login login;
	
	private void init_good(){
		this.fieldFactory = new MyFieldFactory();
		session = new UserSession(this);
		final Window mainWindow = new Window("Vaadin users");
		
		//Layout
		
		VerticalLayout mainLay = new VerticalLayout();
		HorizontalLayout upLay = new HorizontalLayout();
		HorizontalLayout centerLay = new HorizontalLayout();
		VerticalLayout leftLay = new VerticalLayout();
		final HorizontalLayout center = new HorizontalLayout();
		mainWindow.addComponent(mainLay);
		mainLay.setWidth("100%");
		mainWindow.setScrollable(true);
		mainLay.addComponent(upLay);
		upLay.setHeight("100px");
		mainLay.setComponentAlignment(upLay, Alignment.MIDDLE_CENTER);
		mainLay.addComponent(centerLay);
		centerLay.setWidth("100%");
		centerLay.addComponent(leftLay);
		//leftLay.setWidth("20%");
		centerLay.addComponent(center);
		//center.setWidth("100%");
	
		centerLay.setExpandRatio(leftLay, 1.0f);
		centerLay.setExpandRatio(center, 2.0f);

				
		
		//Declaration et configuration des composants
		login = new Login(session);
		final TableUser table_users = new TableUser(session);
		final TableReservation table_reservations = new TableReservation(session);
		final TableCity table_city = new TableCity(session);
		final TableRestaurant table_restaurants = new TableRestaurant(session);
		final Formulaire form = new Formulaire(Reservation.class, session);

		Label titre = new Label("RESTaurant");
		titre.setStyleName("h1");
		
		Button button_restaurant = new Button("Restaurants");
		button_restaurant.addListener(new ClickListener() {
			
			public void buttonClick(ClickEvent event) {
				center.removeAllComponents();
				center.addComponent(table_restaurants.getPane());
			}
		});
		Button button_city = new Button("Villes");
		button_city.addListener(new ClickListener() {
			
			public void buttonClick(ClickEvent event) {
				center.removeAllComponents();
				center.addComponent(table_city.getPane());
			}
		});
		Button button_reservations = new Button("RŽservations");
		button_reservations.addListener(new ClickListener() {
			
			public void buttonClick(ClickEvent event) {
				center.removeAllComponents();
				center.addComponent(table_reservations.getPane());
			}
		});
		Button button_users = new Button("Users");
		button_users.addListener(new ClickListener() {
			
			public void buttonClick(ClickEvent event) {
				center.removeAllComponents();
				center.addComponent(table_users.getPane());
			}
		});
		
		//Ajout des composants
		form.setStyleName("form");	
		
		upLay.addComponent(titre);
		centerLay.addComponent(leftLay);
		
		leftLay.addComponent(button_reservations);
		button_reservations.setWidth("100px");
		//leftLay.setExpandRatio(button_reservations, 1.0f);
		
		leftLay.addComponent(button_restaurant);
		button_restaurant.setWidth("100px");
		//leftLay.setExpandRatio(button_restaurant, 1.0f);

		leftLay.addComponent(button_users);
		button_users.setWidth("100px");
		//leftLay.setExpandRatio(button_users, 1.0f);
		
		leftLay.addComponent(button_city);
		button_city.setWidth("100px");
		leftLay.setExpandRatio(button_city, 1.0f);
		
		
		centerLay.addComponent(center);
		center.addComponent(login);
		table_users.setWidth("100%");
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
