package DBDAO;

import Beans.Company;
import Exceptions.EntityCrudException;

import java.util.List;

public interface CompanyDAO {
    Integer createCompany(Company company) throws EntityCrudException;
    Company readCompany(Integer companyId) throws EntityCrudException;
    List<Company> readAllCompanies() throws EntityCrudException;
    void updateCompany(Company company) throws EntityCrudException;
    void deleteCompany(Integer companyId) throws EntityCrudException;
    boolean isCompanyExist(String name, String email) throws EntityCrudException;
}
