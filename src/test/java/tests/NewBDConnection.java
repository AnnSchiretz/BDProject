package tests;

import database.DBConnection;
import models.Group;
import org.testng.annotations.Test;


public class NewBDConnection {
    @Test
    public void connect() throws Exception {
        DBConnection connection = new DBConnection();
        connection.selectFromTable("qa02.group");
        System.out.println(connection.selectFromTable("qa02.group"));
        connection.selectFromTable("qa02.group", 1);
        //connection.insertIntoTable("qa02.group", new Group("FBJEB","2020-09-03"));
        connection.updateIntoTable("qa02.group",3,new Group("yyyyyyy","2020-10-09") );
        connection.deleteFromTable("qa02.group", 5);
        //        connection.selectFromTable("qa02.student");
//        connection.selectFromTable("qa02.homeworks");

    }

}
