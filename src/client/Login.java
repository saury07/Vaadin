package client;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import hibernate.HbnContainer;
import hibernate.HbnContainer.SessionManager;

import backoffice.bean.User;

import com.gargoylesoftware.htmlunit.javascript.host.Text;
import com.vaadin.data.Validator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Form;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;

public class Login extends Form {

	SessionManager session;
	boolean valid = false;
	
	public Login(final SessionManager session){

		this.session = session;
		setCaption("Identification");

		TextField login = new TextField("Adresse mail");
		//login.setRequired(true);
		TextField password = new TextField("Password");
		//password.setRequired(true);

		password.setSecret(true);

		
		UserValidator dbValidator = new UserValidator(session);
		login.addValidator(dbValidator);
		Validator passValidator = new PasswordValidator(this, dbValidator, login);
		password.addValidator(passValidator);
		Button commit = new Button("Valider", this, "commit");

		getLayout().addComponent(login);
		getLayout().addComponent(password);
		getLayout().addComponent(commit);

	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
}

class PasswordValidator implements Validator{

	UserValidator uv;
	TextField user;
	Login login;
	
	public PasswordValidator(Login l, UserValidator uv, TextField t){
		this.uv = uv;
		user = t;
		login = l;
	}

	public void validate(Object value) throws InvalidValueException {
		uv.validate(user.getValue());
		login.setValid(isValid(value) && uv.isValid(user.getValue()));
		if(!isValid(value))
			throw new InvalidValueException("Mot de passe incorrect.");	
	}

	public boolean isValid(Object value) {
		if(uv.getUser() != null){
			return uv.getUser().getPassword().equals(value);
		}
		return false;
	}

}

class UserValidator implements Validator{

	private SessionManager session;
	private User user;

	public UserValidator(SessionManager session){
		this.session = session;
	}

	public User getUser(){
		return user;
	}

	public void validate(Object value) throws InvalidValueException {
		if(!isValid(value)){
			throw new InvalidValueException("Adresse incorrecte.");				
		}
	}

	public boolean isValid(Object value) {
		HbnContainer<User> cont = new HbnContainer<User>(User.class, session);
		Collection l = cont.getItemIds();
		Iterator i = l.iterator();
		while(i.hasNext()){
			User u = (User)session.getSession().get(User.class, (Serializable)i.next());
			if(value.equals(u.getMail())){
				user = u;
				return true;
			}
		}

		return false;
	}

}


