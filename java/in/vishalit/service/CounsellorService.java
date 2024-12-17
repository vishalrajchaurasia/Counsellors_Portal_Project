package in.vishalit.service;

import in.vishalit.dto.DashBoardResponse;
import in.vishalit.entity.Counsellor;

public interface CounsellorService {
	
	public Counsellor findByEmail(String email);
	
	public boolean register(Counsellor counsellor);
	
	public Counsellor login(String email,String pwd);
	
	public DashBoardResponse getDashboardInfo(Integer counsellorId);
}
