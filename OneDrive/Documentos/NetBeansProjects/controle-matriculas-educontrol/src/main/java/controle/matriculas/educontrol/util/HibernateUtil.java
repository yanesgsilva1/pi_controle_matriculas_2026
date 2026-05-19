/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controle.matriculas.educontrol.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Gerenciador de Sessões do Hibernate para o sistema EduControl.
 * Responsável por ler o arquivo hibernate.cfg.xml e conectar ao MySQL.
 * 
 * @author Cauã Rogerio Silva Ferreira
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();
    
    private static SessionFactory buildSessionFactory() {
        StandardServiceRegistry registry = null;
        try {
            // Inicializa o registro de serviços lendo as configurações do hibernate.cfg.xml
            registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build();
            
            // Constrói a fábrica de sessões a partir dos metadados mapeados
            return new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();
                
        } catch (Exception e) {
            System.err.println("\n========================================================");
            System.err.println("ERRO CRÍTICO: Falha ao iniciar a SessionFactory do Hibernate!");
            System.err.println("Verifique se o MySQL está rodando e se a senha está correta.");
            System.err.println("========================================================\n");
            e.printStackTrace();
            
            // Destrói o registro criado para evitar vazamento de memória se houver falha
            if (registry != null) {
                StandardServiceRegistryBuilder.destroy(registry);
            }
            
            throw new ExceptionInInitializerError("Não foi possível carregar as configurações do banco: " + e);
        }
    }
    
    /**
     * Retorna a instância única da fábrica de sessões.
     * @return SessionFactory
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    /**
     * Fecha as conexões com o banco de dados e limpa os caches.
     */
    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            getSessionFactory().close();
        }
    }
}
