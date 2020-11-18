package Conexion;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Scanner;

import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ORM.DatosProf;
import ORM.Empleado;

public class Ejercicio_4 {
	
	static Scanner entrada = new Scanner(System.in);
	public static void asig_datos_prof_empleado(ORM.Empleado e) {
		try (Session s = HibernateUtil.getSessionFactory().openSession()){
			Transaction t = s.beginTransaction();
			try {
				
				ORM.DatosProf d = new ORM.DatosProf();
				d.setEmpleado(e);
				
				System.out.print("Introduce le sueldo bruto: ");
				BigDecimal sueldoBruto = new BigDecimal(new BigInteger(entrada.nextLine()), 0);
				d.setSueldoBrutoAnual(sueldoBruto);
				
				System.out.print("Introduce la categoria: ");
				d.setCategoria(entrada.nextLine());
				
				s.save(d);
			
				t.commit();
			}catch(RollbackException ex) {
				t.rollback();
				System.out.println("Error al guardar los datos en la base de datos.\nHaciendo RollBack");
			}
		}
	}
	
	public static void asig_datos_prof_empleado() {
		try (Session s = HibernateUtil.getSessionFactory().openSession()){
			Transaction t = s.beginTransaction();
			try {
				
				ORM.DatosProf d = new ORM.DatosProf();
				List<ORM.Empleado> le = (List<ORM.Empleado>)s.createQuery("FROM Empleado").getResultList();
				
				System.out.println("Seleccione el empleado a añadir datos: ");
				for(ORM.Empleado e : le)
					System.out.println((le.indexOf(e) + 1) + ". " + e.getDni());
				
				System.out.print("\nIntroduce el empleado: ");		
				int option = Integer.parseInt(entrada.nextLine());
				ORM.Empleado e = le.get(option);
				asig_datos_prof_empleado(e);
				
				t.commit();
			}catch(RollbackException e) {
				t.rollback();
				System.out.println("Error al guardar los datos en la base de datos.\nHaciendo RollBack");
			}catch(NumberFormatException e) {
				System.out.println("Error: Al selecionar empleado se inserto una letra");
			}
		}
	}
	
	public static Empleado create_empleado() {
		try (Session s = HibernateUtil.getSessionFactory().openSession()){
			Transaction t = null;
			try {
				t = s.beginTransaction();
				
				ORM.Empleado e = new ORM.Empleado();
				
				System.out.print("Introduce el DNI: ");
				e.setDni(entrada.nextLine());
				
				System.out.print("Introduce el Nombre: ");
				e.setNomEmp(entrada.nextLine());
				
				s.save(e);
				
				t.commit();
				
				return e;
			}catch(RollbackException e) {
				t.rollback();
			}catch(PersistenceException e) {
				System.out.println("Error: Ya existe un Empleado con ese DNI.");
				t.rollback();
			}
		}
		return null;
	}
}
