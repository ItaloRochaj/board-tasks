package board.tasks.ui;


import board.tasks.persistence.migration.MigrationStrategy;

import java.sql.SQLException;

import static board.tasks.persistence.config.ConnectionConfig.getConnection;

public class Main {

    public static void main(String[] args) throws SQLException {
        try(var connection = getConnection()){
            new MigrationStrategy(connection).executeMigration();
        }

    }

}