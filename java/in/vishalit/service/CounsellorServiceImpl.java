package in.vishalit.service;

import java.util.List;


import org.springframework.stereotype.Service;

import in.vishalit.dto.DashBoardResponse;
import in.vishalit.entity.Counsellor;
import in.vishalit.entity.Enquiry;
import in.vishalit.repo.CounsellorRepo;
import in.vishalit.repo.EnquiryRepo;

@Service
public class CounsellorServiceImpl implements CounsellorService {

	
	private CounsellorRepo counsellorRepo;
	private EnquiryRepo enqRepo;
	
	public CounsellorServiceImpl(CounsellorRepo counsellorRepo, EnquiryRepo enqRepo) {
		super();
		this.counsellorRepo = counsellorRepo;
		this.enqRepo = enqRepo;
	}
	
	@Override
	public Counsellor findByEmail(String email) {
		return counsellorRepo.findByEmail(email);
	}

	@Override
	public boolean register(Counsellor counsellor) {
		Counsellor savedCounsellor = counsellorRepo.save(counsellor);
		if(null !=savedCounsellor.getCounsellorId()) {
			return true;
		}
		return false;
	}

	@Override
	public Counsellor login(String email, String pwd) {
		Counsellor counsellor = counsellorRepo.findByEmailAndPwd(email, pwd);
		return counsellor;
	}

	@Override
	public DashBoardResponse getDashboardInfo(Integer counsellorId) {
		
		DashBoardResponse response = new DashBoardResponse();
		
		List<Enquiry> enqList = enqRepo.getEnquriesByCounsellorId(counsellorId);

	    int totalEnq = enqList.size();

	    int enrolledEnqs = (int) enqList.stream()
	            .filter(e -> "Enrolled".equals(e.getEnqStatus()))
	            .count();

	    int lostEnqs = (int) enqList.stream()
	            .filter(e -> "Lost".equals(e.getEnqStatus()))
	            .count();

	    int openEnqs = (int) enqList.stream()
	            .filter(e -> "Open".equals(e.getEnqStatus()))
	            .count();

	    // Setting each value correctly
	    response.setTotalEnqs(totalEnq);
	    response.setEnrolledEnqs(enrolledEnqs);
	    response.setLostEnqs(lostEnqs);
	    response.setOpenEnqs(openEnqs);
		
		return response;
	}

	

}
