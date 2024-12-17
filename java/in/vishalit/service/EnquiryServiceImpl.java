package in.vishalit.service;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import in.vishalit.dto.ViewEnqsFilterRequest;
import in.vishalit.entity.Counsellor;
import in.vishalit.entity.Enquiry;
import in.vishalit.repo.CounsellorRepo;
import in.vishalit.repo.EnquiryRepo;
import io.micrometer.common.util.StringUtils;

@Service
public class EnquiryServiceImpl implements EnquiryService {
	
	
	private EnquiryRepo enqRepo;
	private CounsellorRepo counsellorRepo;
	

	public EnquiryServiceImpl(EnquiryRepo enqRepo, CounsellorRepo counsellorRepo) {
		this.enqRepo = enqRepo;
		this.counsellorRepo = counsellorRepo;
	}


	@Override
	public boolean addEnquiry(Enquiry enq, Integer counsellorId) throws Exception {
		
		Counsellor counsellor = counsellorRepo.findById(counsellorId).orElse(null);
		
		if(counsellor==null) {
			throw new Exception("No Counsellor found");
		}
		
		//associating counsellor to enquiry
		enq.setCounsellor(counsellor);
		
		Enquiry save = enqRepo.save(enq);
		
		if(save.getEnqId() !=null) {
			return true;
		}
		
		return false;
	}

	@Override
	public List<Enquiry> getAllEnquiries(Integer counsellorId) {
		return enqRepo.getEnquriesByCounsellorId(counsellorId);
		
	}
	
	@Override
	public Enquiry getEnquiryById(Integer enqId) {
		return enqRepo.findById(enqId).orElse(null);
		 
	}
	
	@Override
	public List<Enquiry> getEnquiresWithFilter(ViewEnqsFilterRequest filterReq, Integer counsellorId) {
		//QBE implementation (Dynamic Query Preparation)
		
		Enquiry enq = new Enquiry();// entity
		
		if(StringUtils.isNotEmpty(filterReq.getClassMode())) {
			enq.setClassMode(filterReq.getClassMode());
		}
		
		if(StringUtils.isNotEmpty(filterReq.getCourseName())) {
			enq.setCourseName(filterReq.getCourseName());
		}
		
		if(StringUtils.isNotEmpty(filterReq.getEnqStatus())) {
			enq.setEnqStatus(filterReq.getEnqStatus());
		}
		
		Counsellor c = counsellorRepo.findById(counsellorId).orElse(null);
		enq.setCounsellor(c);
		
		Example<Enquiry> of = Example.of(enq); //dynamics query
		List<Enquiry> enqList = enqRepo.findAll(of);
		
		return enqList;
	}

	

}
