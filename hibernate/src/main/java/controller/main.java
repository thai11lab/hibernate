package controller;

import java.nio.channels.SeekableByteChannel;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import datasource.HibernateConfig;
import model.Employee;

public class main {
	
	private static Session session;
	public static void main(String []aStrings) {
//		List<Employee> employees= getList();
//		for (Employee e : employees) {
//			System.out.println("fullname : "+e.getFullName()+" department : "+e.getDepartment());
//		}
		
//		inserEmployee();
//		updateEmployee(1L);
		deleteEmployee(1L);
	}
	
	public static List<Employee> getList(){
		List<Employee> employees = new ArrayList<Employee>();
		try {
			session = HibernateConfig.buildSessionFactory().openSession();
			session.beginTransaction();
			employees = session.createQuery("select e from Employee e",Employee.class).getResultList();
		}catch (Exception e) {
			if (session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
		}finally {
			
			if(session != null) {
				session.close();				
			}
			
		}
		
		return employees;
	}
	
	public static void inserEmployee() {
		try {
			session = HibernateConfig.buildSessionFactory().openSession();
			session.beginTransaction();
			
			for (int i = 0; i < 5; i++) {
				Employee employee = new Employee();
				employee.setFullName("Dam Anh A "+i);
				employee.setDepartment("Quan li "+i);
				employee.setLevel("Bac "+i);
				session.save(employee);
			}
			session.getTransaction().commit();
		}catch (Exception e) {
			e.printStackTrace();
			if(session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
		}finally {
			session.close();
		}		
	}
	
	public static void updateEmployee(Long id) {
		try {
			session = HibernateConfig.buildSessionFactory().openSession();
			session.beginTransaction();
			
			Employee em = session.find(Employee.class,id);
			em.setFullName("Dam Anh Thai");
			session.getTransaction().commit();
		}catch (Exception e) {
			// TODO: handle exception
			if(session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
		}finally {
			session.close();
		}
	}
	
	public static void deleteEmployee(Long id) {
		try {
			session = HibernateConfig.buildSessionFactory().openSession();
			session.beginTransaction();
			
			Employee employee = session.find(Employee.class,id);
			session.delete(employee);
			session.getTransaction().commit();
			
			System.out.println("Delete Success");
		}catch (Exception e) {
			// TODO: handle exception
			if (session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
		}finally {
			session.close();
		}
	}
}
