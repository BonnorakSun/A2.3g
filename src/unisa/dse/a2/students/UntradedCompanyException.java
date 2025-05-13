package unisa.dse.a2.students;

import java.lang.Exception;

public class UntradedCompanyException extends Exception{
	
	public UntradedCompanyException(String companyCode){
		super(companyCode + "is not a listed company on this exchange");
	}
}
