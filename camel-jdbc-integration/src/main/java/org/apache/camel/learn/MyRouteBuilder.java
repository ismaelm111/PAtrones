package org.apache.camel.learn;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.csv.CsvDataFormat;
/**
 * A Camel Java DSL Router
 */
public class MyRouteBuilder extends RouteBuilder {

    
    /**
     * Let's configure the Camel routing rules using Java code...
     */
    public void configure() {

         CsvDataFormat csv = new CsvDataFormat();
        csv.setDelimiter(',');
       
      //  from("file:src/datos?noop=true&filename=cardsclients.csv")       
      from("sftp:localhost:22/upload?noop=true&username=practica&password=pass")
       .unmarshal()
       .csv()         
       .process(new ProcesarArchivo())       
       .to("jdbc:myDataSource") ;
    
    }

}
