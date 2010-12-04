package hibernate;

import hibernate.HbnContainer.SessionManager;

import org.hibernate.Session;

import com.vaadin.Application;
import com.vaadin.service.ApplicationContext.TransactionListener;

public class UserSession implements SessionManager{

	private Application app;
	
	
	
	public UserSession(Application app) {
		super();
		this.app = app;
	}

	/**
	 * Renvoie la session active. Sert aussi pour les transactions
	 */
	public Session getSession() {
		Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
		if (!currentSession.getTransaction().isActive()) {
			currentSession.beginTransaction();
		}
		return currentSession;
	}

	/**
	 * Fermeture de la session si elle n'est pas active
	 */
	private void closeSession() {
		Session sess = HibernateUtil.getSessionFactory().getCurrentSession();
		if (sess.getTransaction().isActive()) {
			sess.getTransaction().commit();
		}
		if (sess.isOpen()) {
			sess.close();
		}
	}

	/**
	 * Vérifie que la session n'est pas active pour assurer qu'elle soit bien fermée quand il faut
	 */
	public void addTransactionListener(){
		app.getContext().addTransactionListener(new TransactionListener() {

			public void transactionStart(Application application, Object transactionData) {
				// TODO Auto-generated method stub

			}

			public void transactionEnd(Application application, Object transactionData) {
				if(application == app){
					closeSession();
				}
			}
		});
	}
	
}
