package dao;

public class DAOFactory {
    public static ItemDAO criarItemDAO() {
        return new ItemDAOJDBC();
    }
}
