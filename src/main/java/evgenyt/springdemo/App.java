package evgenyt.springdemo;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.List;

/**
 * Hibernate Native SQl demo
 * We can query db using db names od tables and columns via NativeQuery class
 * Aug 2019 EvgenyT
 */

public class App {
    public static void main( String[] args ) {
        // Get application context from file
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContext.xml");
        // Get factory bean and create session
        SessionFactory sessionFactory = context.getBean("sessionFactory",
                SessionFactory.class);
        Session session = sessionFactory.openSession();
        // Scalar query
        String sql = "SELECT person_name FROM person";
        NativeQuery query = session.createSQLQuery(sql);
        List results = query.list();
        System.out.println(Arrays.toString(results.toArray()));
        // Entity query
        String sqlEnt = "SELECT * FROM person";
        NativeQuery queryEnt = session.createSQLQuery(sqlEnt);
        queryEnt.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List resultsEnt = queryEnt.list();
        System.out.println(Arrays.toString(resultsEnt.toArray()));
        // Named and parametrized query
        String sqlPar = "SELECT * FROM person WHERE person_id = :id";
        NativeQuery queryPar = session.createSQLQuery(sqlPar);
        queryPar.addEntity(PersonEntity.class);
        queryPar.setParameter("id", 1);
        List resultsPar = queryPar.list();
        System.out.println(Arrays.toString(resultsPar.toArray()));
        session.close();
    }
}
