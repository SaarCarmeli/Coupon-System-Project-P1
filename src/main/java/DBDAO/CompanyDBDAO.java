package DBDAO;

import Beans.Company;
import DB.Util.DBManager;
import DB.Util.DBTools;
import DB.Util.ObjectExtractionUtil;
import Exceptions.CrudOperation;
import Exceptions.EntityCrudException;
import Exceptions.EntityType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompanyDBDAO implements CompanyDAO {
    private static CompanyDBDAO instance = null;

    private CompanyDBDAO() {
    }

    /**
     * Static method for retrieving and/or initiating an instance of CompanyDBDAO.
     *
     * @return CompanyDBDAO instance
     */
    public static CompanyDBDAO getInstance() {
        if (instance == null) {
            synchronized (CompanyDBDAO.class) {
                if (instance == null) {
                    instance = new CompanyDBDAO();
                }
            }
        }
        return instance;
    }

    /**
     * Create Company record in MySQL database.
     *
     * @param company Company instance to create record by
     * @throws EntityCrudException Thrown if Create in MySQL was unsuccessful
     */
    @Override
    public void createCompany(Company company) throws EntityCrudException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, company.getName());
        params.put(2, company.getEmail());
        params.put(3, company.getPassword());
        try {
            System.out.println("Created Company: " + DBTools.runQuery(DBManager.CREATE_COMPANY, params));
        } catch (SQLException e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.CREATE);
        }
    }

    /**
     * Returns an instance of Company from MySQL database by company ID number.
     * Returns Company ID number, Name and Email. Does not return password.
     *
     * @param companyId Company ID number
     * @return Company instance from MySQL database
     * @throws EntityCrudException Thrown if Read from MySQL was unsuccessful
     */
    @Override
    public Company readCompany(Integer companyId) throws EntityCrudException {
        ResultSet result;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, companyId);
        try {
            result = DBTools.runQueryForResult(DBManager.READ_COMPANY_BY_ID, params);
            assert result != null;
            result.next();
            return ObjectExtractionUtil.resultSetToCompany(result, CouponDBDAO.getInstance().readCouponsByCompanyId(companyId));
        } catch (SQLException e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.READ);
        }
    }

    /**
     * Returns a List of all Companies in MySQL database
     *
     * @return List of all Companies in MySQL database
     * @throws EntityCrudException Thrown if Read from MySQL was unsuccessful
     */
    @Override
    public List<Company> readAllCompanies() throws EntityCrudException {
        ResultSet result;
        List<Company> companyList = new ArrayList<>();
        try {
            result = DBTools.runQueryForResult(DBManager.READ_ALL_COMPANIES);
            while (result.next()) {
                companyList.add(ObjectExtractionUtil.resultSetToCompany(result));//todo CouponDBDAO.getInstance().readCouponsByCompanyId(ObjectExtractionUtil.resultSetToCompany(result).getId())??? too dangerous?
            }
            return companyList;
        } catch (SQLException e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.READ);
        }
    }

    /**
     * Updates Company record in MySQL database.
     * Updates Email and Password. Does not update Company ID number and name.
     *
     * @param company Company instance to update record by
     * @throws EntityCrudException Thrown if update in MySQL was unsuccessful
     */
    @Override
    public void updateCompany(Company company) throws EntityCrudException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, company.getEmail());
        params.put(2, company.getPassword());
        params.put(3, company.getId());
        try {
            System.out.println("Updated Company: " + DBTools.runQuery(DBManager.UPDATE_COMPANY_BY_ID, params));
        } catch (SQLException e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.UPDATE);
        }
    }

    /**
     * Deletes Company record from MySQL database by company ID number.
     *
     * @param companyId ID number of the Company to be deleted
     * @throws EntityCrudException Thrown if delete from MySQL was unsuccessful
     */
    @Override
    public void deleteCompany(Integer companyId) throws EntityCrudException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, companyId);
        try {
            System.out.println("Deleted Company: " + DBTools.runQuery(DBManager.DELETE_COMPANY_BY_ID, params));
        } catch (SQLException e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.DELETE);
        }
    }

    /**
     * Checks whether the Company corresponding to the name or email arguments exists in MySQL database
     * by counting the companies that meet the criteria.
     *
     * @param name  Company name
     * @param email Company email
     * @return true -> company exists, false -> company does not exist
     * @throws EntityCrudException Thrown if count in MySQL was unsuccessful
     */
    @Override
    public boolean isCompanyExist(String name, String email) throws EntityCrudException {
        int counter;
        ResultSet result;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, name);
        params.put(2, email);
        try {
            result = DBTools.runQueryForResult(DBManager.COUNT_COMPANIES_BY_NAME_OR_EMAIL, params);
            assert result != null;
            result.next();
            counter = result.getInt(1);
            return counter != 0;
        } catch (SQLException e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.COUNT);
        }
    }
}
