package dbService.dao;

import dbService.dataSets.UserDataSet;
import dbService.executor.Executor;

import java.sql.Connection;
import java.sql.SQLException;

public class UserDAO {
    private Executor executor;

    public UserDAO(Connection connection) {
        this.executor = new Executor(connection);
    }

    public UserDataSet get(String login) throws SQLException {
        return executor.execQuery("select * from users where login='" + login + "'", result -> {
            result.next();
            return new UserDataSet(result.getString("login"),
                    result.getString("password"),
                    result.getString("email"));
        });
    }
    public String getUserLogin(String login) throws SQLException {
        return executor.execQuery("select * from users where login='" + login + "'", result -> {
            result.next();
            return result.getString("login");
        });
    }

    public void insertUser(String login, String password, String email) throws SQLException {
        executor.execUpdate("insert into users (login, password, email) values ('" + login + "', '"+ password +"', '"+ email +" ')");
    }

    public void createTable() throws SQLException {
        executor.execUpdate("create table if not exists users (login varchar(256), password varchar(256), email varchar(256), primary key (login))");
    }

    public void dropTable() throws SQLException {
        executor.execUpdate("drop table users");
    }
}
