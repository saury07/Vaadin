package com.example.vaadin;

import hibernate.HbnContainer;
import hibernate.HbnContainer.SessionManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import com.example.bean.Reservation;
import com.example.bean.Restaurant;
import com.example.bean.User;
import com.example.bean.Ville;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.Select;
import com.vaadin.ui.Button.ClickEvent;

public class Formulaire extends Form {

	Class classe;
	Object bean;
	final SessionManager session;

	public Formulaire(Class classe, final SessionManager session) {
		super();
		this.classe = classe;
		this.session = session;
		setImmediate(true);
		if(classe == User.class){
			bean = new User();
			((User)bean).setDate(new Date());
			setItemDataSource(new BeanItem(bean));
			setFormFieldFactory(new UserFieldFactory());
			ArrayList<String> ordre = new ArrayList();
			ordre.add("genre");ordre.add("prenom");ordre.add("nom");ordre.add("telephone");
			setVisibleItemProperties(ordre);
			setCaption("Ajouter utilisateur");
			//getField("genre").setValue("M.");
		}
		if(classe == Restaurant.class){
			bean = new Restaurant();
			((Restaurant)bean).setVille(new Ville());
			setItemDataSource(new BeanItem(bean));
			setFormFieldFactory(new RestaurantFactory(session));
			ArrayList<String> ordre = new ArrayList();
			ordre.add("nom");ordre.add("ville");ordre.add("couverts");
			setVisibleItemProperties(ordre);
			setCaption("Ajouter restaurant");
		}
		if(classe == Reservation.class){
			bean = new Reservation();
			setItemDataSource(new BeanItem(bean));
			setFormFieldFactory(new ReservationFactory(session));
			ArrayList<String> ordre = new ArrayList();
			ordre.add("user");ordre.add("restaurant");ordre.add("couverts");ordre.add("date");
			setVisibleItemProperties(ordre);
			setCaption("Ajouter reservation");
		}
		Button commit = new Button("Commit", this, "commit");
		commit.addListener(new ClickListener() {
			
			public void buttonClick(ClickEvent event) {
				session.getSession().merge(bean);
			}
		});
		getLayout().addComponent(commit);
		Button value = new Button("Value");
		value.addListener(new ClickListener() {
			
			public void buttonClick(ClickEvent event) {
				Reservation r = (Reservation) bean;
				ReservationFactory rf = (ReservationFactory)getFieldFactory();
				Restaurant re = rf.getRestaurant();
				System.out.println("Field restaurant : "+re.getId());
				System.out.println(r.getId());
				System.out.println(r.getRestaurant().getNom());
				System.out.println(r.getUser().getNom());
			}
		});
		getLayout().addComponent(value);
	}
}



class UserFieldFactory extends DefaultFieldFactory{
	public Field createField(Item item, Object propertyId, Component uiContext) {

		String f = (String) propertyId;
		if(f.equals("genre")){
			Select select = new Select();
			select.setCaption("Genre");
			select.setNewItemsAllowed(false);
			select.addItem("M.");
			select.addItem("Mme.");
			select.addItem("Melle.");
			return select;
		}

		return super.createField(item, propertyId, uiContext);
	}
}


class RestaurantFactory extends DefaultFieldFactory{

	private SessionManager session;
	public RestaurantFactory(SessionManager session){
		this.session = session;
	}

	public Field createField(Item item, Object propertyId, Component uiContext) {
		String f = (String) propertyId;
		if(f.equals("ville")){
			Select select = new Select();
			select.setContainerDataSource(new HbnContainer<Ville>(Ville.class, session));
			select.setItemCaptionMode(
		            Select.ITEM_CAPTION_MODE_PROPERTY);
		    select.setItemCaptionPropertyId("nom");
			select.setCaption("Ville");
			select.setNewItemsAllowed(false);
			return select;
		}
		return super.createField(item, propertyId, uiContext);
	}
}

class ReservationFactory extends DefaultFieldFactory{
	private SessionManager session;
	private Restaurant restaurant;
	public ReservationFactory(SessionManager session){
		this.session = session;
	}
	
	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Field createField(Item item, Object propertyId, Component uiContext) {
		String f = (String) propertyId;
		if(f.equals("restaurant")){
			
			Select select = new Select(){

				@Override
				public void setValue(Object newValue, boolean repaintNeed) throws ReadOnlyException,
						ConversionException {
					if(newValue != null){
						Restaurant r = (Restaurant) session.getSession().get(Restaurant.class, (Serializable)newValue);
						System.out.println(r.getNom());
						restaurant = r;
						super.setValue(r.getId(), repaintNeed);
						
					}
					else{
						super.setValue(newValue, repaintNeed);
					}
				}

				
				
			};
			select.setContainerDataSource(new HbnContainer<Restaurant>(Restaurant.class, session));
			select.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
		    select.setItemCaptionPropertyId("nom");
			select.setCaption("Restaurant");
			select.setNewItemsAllowed(false);
			return select;
		}
		else if(f.equals("user")){
			Select select = new Select(){

				@Override
				public void setValue(Object newValue, boolean repaintNeed) throws ReadOnlyException,
						ConversionException {
					if(newValue != null){
						User u = (User) session.getSession().get(User.class, (Serializable)newValue);
						System.out.println(u.getNom());
						super.setValue(u.getId(), repaintNeed);
					}
					else{
						super.setValue(newValue, repaintNeed);
					}
				}

				
				
			};
			select.setContainerDataSource(new HbnContainer<User>(User.class, session));
			select.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
		    select.setItemCaptionPropertyId("nom");
			select.setCaption("Utilisateur");
			select.setNewItemsAllowed(false);
			return select;
		}
		return super.createField(item, propertyId, uiContext);
	}
}

