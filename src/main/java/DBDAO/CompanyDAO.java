package DBDAO;

import Beans.Company;
import Exceptions.EntityCrudException;

import java.util.List;

public interface CompanyDAO {
    Integer createCompany(final Company company) throws EntityCrudException;
    Company readCompany(final Integer companyId) throws EntityCrudException;
    List<Company> readAllCompanies() throws EntityCrudException;
    void updateCompany(final Company company) throws EntityCrudException;
    void deleteCompany(final Integer companyId) throws EntityCrudException;
    boolean isCompanyExist(final String name, final String email) throws EntityCrudException;
}
