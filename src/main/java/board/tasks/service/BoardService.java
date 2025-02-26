package board.tasks.service;

import board.tasks.persistence.dao.BoardColumnDAO;
import board.tasks.persistence.dao.BoardDAO;
import board.tasks.persistence.entity.BoardColumnEntity;
import board.tasks.persistence.entity.BoardEntity;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class BoardService {

    private final Connection connection;

    public BoardEntity insert(final BoardEntity entity) throws SQLException {
        var dao = new BoardDAO(connection);
        var boardColumnDAO = new BoardColumnDAO(connection);
        try {
            dao.insert(entity);
            List<BoardColumnEntity> columns = entity.getBoardColumns().stream().map(c -> {
                c.setBoard(entity);
                return c;
            }).toList();;
            for (var column: columns){
                boardColumnDAO.insert(column);
            }
        connection.commit();
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
