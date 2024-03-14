package org.apache.camel.learn;

import javax.sql.DataSource;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultRegistry;
import org.apache.commons.dbcp.BasicDataSource;

/**
 * A Camel Application
 */
public class MainApp {

    public static void main(String... args) throws Exception {
        String URLJDBC = "jdbc:postgresql://localhost:5432/postgres";

        DataSource dataSource = source(URLJDBC);

        DefaultRegistry reg = new DefaultRegistry();
        reg.bind("myDataSource", dataSource);

        CamelContext context = new DefaultCamelContext(reg);
        context.addRoutes(new MyRouteBuilder());
        context.getShutdownStrategy().setTimeout(1000);
        
        context.start();

        Thread.sleep(10000);
        context.stop();

    }

    private static DataSource source(String jdbcURL) {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUsername("camel");
        ds.setPassword("camel");
        ds.setUrl(jdbcURL);
        return ds;
    }

}
