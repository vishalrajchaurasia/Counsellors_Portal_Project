package in.vishalit.service;

import java.util.List;

import in.vishalit.dto.ViewEnqsFilterRequest;
import in.vishalit.entity.Enquiry;

public interface EnquiryService {
	
	public boolean addEnquiry(Enquiry enq,Integer counsellorId) throws Exception;
	
	public List<Enquiry> getAllEnquiries(Integer counsellorId);
	
	public List<Enquiry> getEnquiresWithFilter(ViewEnqsFilterRequest filterReq,Integer counsellorId);
	
	public Enquiry getEnquiryById(Integer enqId);
}
