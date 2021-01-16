package DAO;

public class DaoSqls {
    // Select 관련
    public static final String SELECT_All = "SELECT * FROM user";
    public static final String SELECT_BY_ID = "SELECT * FROM user WHERE id := userId";

    // Update 관련
    public static final String UPDATE = "UPDATE user SET name := username WHERE id := userId";

    // DELETE 관련
    public static final String DELETE = "DELETE * FROM user WHERE id := userId";
}
