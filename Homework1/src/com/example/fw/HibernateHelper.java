package com.example.fw;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.example.fw.ObjGroup;
import com.example.utilits.SortedListOf;

public class HibernateHelper extends BaseHelper {

	public HibernateHelper(ApplicationManager manager) {
	  super(manager);
	}

	public SortedListOf<ObjGroup> listGroups() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction trans = session.beginTransaction();
		try {
			return new SortedListOf<ObjGroup>((List<ObjGroup>) session.createQuery("from ObjGroup").list());
		} finally {
			trans.commit();
		}
	}
	
	public SortedListOf<ObjContact> listContacts() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction trans = session.beginTransaction();
		try {
            return new SortedListOf<ObjContact>((List<ObjContact>) session.createQuery("from ObjContact").list());
		} finally {
            trans.commit();
		}
	}
	
	public void insertGroups(List<ObjGroup> groups) {
		Session session;
		for (ObjGroup group : groups) {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			try {
				session.save(group);
			} finally {
				session.getTransaction().commit();
			}
		}
	}
	
	public void insertContact(List<ObjContact> contacts) {
		Session session;
		for (ObjContact contact : contacts) {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			try {
				session.save(contact);
			} finally {
				session.getTransaction().commit();
			}
		}
	}
	
	public void moveContactToGroup(int idContact, int idGroup) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction trans = session.beginTransaction();
		try {
			session
				.createSQLQuery(
						"INSERT INTO `address_in_groups` (id, group_id) "
							+ "VALUES ('" + idContact + "', '" + idGroup + "')")
				.executeUpdate();
		} finally {
          trans.commit();
		}
	}	
		
	public void dbClear() {
		truncateTable("`group_list`");
		truncateTable ("`addressbook`");
		truncateTable ("`address_in_groups`");
		
	}	
	
	private void truncateTable (String table) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction trans = session.beginTransaction();
		try {
			session.createSQLQuery("truncate " + table).executeUpdate();
		} finally {
          trans.commit();
		}
	} 
	
	public void dbClose() {
	}	

}
