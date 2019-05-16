/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TEST;

import basededatos.Trabajo;
import basededatos.Usuario;
import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author PC06
 */
public class Operaciones {
    EntityManagerFactory emf;
    EntityManager em;
    
    public void conexionBaseDatos(){
    // Conectar con la base de datos
        Map<String, String> emfProperties = new HashMap<String, String>();
        emfProperties.put("javax.persistence.schema-generation.database.action", "create");
        emf = Persistence.createEntityManagerFactory("BaseDeDatosPU", emfProperties);
        em = emf.createEntityManager();
    }
    
    public void creaRegistros(){
        conexionBaseDatos();
        // REALIZAR AQUÍ LAS OPERACIONES SOBRE LA BASE DE DATOS
        Trabajo trabajo1 = new Trabajo();
        trabajo1.setIdTrabajo(1);
        trabajo1.setNombre("Programador");
        trabajo1.setEmpresa("serodan");
        trabajo1.setHorasSemanales((short)60);
        trabajo1.setSalario(BigDecimal.valueOf(1200.03));
        
        Trabajo trabajo2 = new Trabajo();
        trabajo2.setIdTrabajo(2);
        trabajo2.setNombre("Profesor");
        trabajo2.setEmpresa("CEIP Reina Fabiola");
        trabajo2.setHorasSemanales((short)40);
        trabajo2.setSalario(BigDecimal.valueOf(1500.59));
        
        Usuario usuario1 = new Usuario();
        usuario1.setIdUsuario(1);
        usuario1.setNombre("Jhon");
        usuario1.setApellidos("Smith Sanchez");
        usuario1.setTrabajo(trabajo1);
        
        Usuario usuario2 = new Usuario();
        usuario2.setIdUsuario(2);
        usuario2.setNombre("Gonzalo");
        usuario2.setApellidos("Lopez España");
        usuario2.setTrabajo(trabajo2);
        
        em.getTransaction().begin();
        em.persist(trabajo1);
        em.persist(trabajo2);
        
        em.persist(usuario1);
        em.persist(usuario2);

        em.getTransaction().commit();
        // Cerrar la conexión con la base de datos
        em.close(); 
        emf.close(); 
        try { 
            DriverManager.getConnection("jdbc:derby:Database;shutdown=true"); 
        } catch (SQLException ex) { 
        }
    }
    
    public void modificaRegistros(){
        //Busca a Jhon y lo cambia a Juan
        
        conexionBaseDatos();
        Query queryUsuarioJhon = em.createNamedQuery("Usuario.findByNombre");
        queryUsuarioJhon.setParameter("nombre", "Jhon");
        List<Usuario> listaUsuarios = queryUsuarioJhon.getResultList();
        em.getTransaction().begin();
        for(Usuario usuarioJhon : listaUsuarios) {
            usuarioJhon.setNombre("Juan");
            em.merge(usuarioJhon);
        }
        em.getTransaction().commit();
        em.close(); 
        emf.close(); 
    }
    
    public void eliminaRegistros(){
        conexionBaseDatos();
        Usuario UsuarioDuplicado = em.find(Usuario.class, 3);
        if(UsuarioDuplicado != null) {
            em.remove(UsuarioDuplicado);
        } else {
            System.out.println("No hay ningun usuario duplicado");
        }
    }
}
