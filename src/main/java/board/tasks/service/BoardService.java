package board.tasks.service;

import board.tasks.persistence.dao.BoardDAO;
import board.tasks.persistence.entity.BoardEntity;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;

@AllArgsConstructor
public class BoardService {

    private final Connection connection;

    public BoardEntity insert(final BoardEntity entity) throws SQLException {
        var dao = new BoardDAO(connection);
        try{
            dao.insert(entity);
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
        return entity;

    }

    public boolean delete(final Long id) throws SQLException{

        var dao = new BoardDAO(connection);
        try {
            if(!dao.exists(id)) {
                return false;
            }

            dao.delete(id);
            connection.commit();
            return true;

        }catch (SQLException e ){
            connection.rollback();
            throw e;
        }


    }

}
