package com.springbootexample.services;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.springbootexample.dao.CategoryRepository;
import com.springbootexample.dao.DesignationRepository;
import com.springbootexample.dao.DurationRepository;
import com.springbootexample.dao.MembershipRepository;
import com.springbootexample.dao.RoleRepository;
import com.springbootexample.dao.ServiceRepository;
import com.springbootexample.dao.StaffRepository;
import com.springbootexample.model.Category;
import com.springbootexample.model.Duration;
import com.springbootexample.model.LoyaltyPoints;
import com.springbootexample.model.Membership;
import com.springbootexample.model.Role;
import com.springbootexample.model.Staff;
import com.springbootexample.model.User;
import com.springbootexample.pojo.ServiceDetails;
import com.springbootexample.pojo.StaffDetails;


@Service("adminService")
public class AdminServiceImpl implements AdminService{
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
    private RoleRepository roleRepository;
	@Autowired
    private DesignationRepository designationRepository;
	@Autowired
    private StaffRepository staffrepository;
	@Autowired
    private DurationRepository durationRepository;
	@Autowired
    private MembershipRepository membershipRepository;
	@Autowired
    private CategoryRepository categoryRepository;
	@Autowired
    private ServiceRepository serviceRepository;
	
	public void saveStaffDetails(StaffDetails staffDetails) {
		Staff staff = new Staff();
		staff.setCommunicationArea(staffDetails.getCommunicationAddress().getArea());
		staff.setCommunicationCity(staffDetails.getCommunicationAddress().getCity());
		staff.setCommunicationStreet(staffDetails.getCommunicationAddress().getStreetName());
		staff.setDateOfBirth(staffDetails.getStaffDateOfBirth());
		staff.setDateOfJoining(staffDetails.getStaffJoiningDate());
		staff.setDesignation(designationRepository.findById(staffDetails.getStaffDesignation()));
		staff.setEmail(staffDetails.getStaffEmail());
		staff.setFatherName(staffDetails.getStaffFatherName());
		staff.setName(staffDetails.getStaffFirstName()+" "+staffDetails.getStaffLastName());
		staff.setPermanentArea(staffDetails.getPermanentAddress().getArea());
		staff.setPermanentCity(staffDetails.getPermanentAddress().getCity());
		staff.setPermanentStreet(staffDetails.getPermanentAddress().getStreetName());
		staff.setPhoneNumber(staffDetails.getStaffPhoneNumber());
		staff.setSalary(2000.0);
		User user = new User();
		user.setActive(1);
		user.setEmail(staffDetails.getStaffEmail());
		user.setLastName(staffDetails.getStaffLastName());
		user.setName(staffDetails.getStaffFirstName());
		user.setRoles(new HashSet<Role>(Arrays.asList(roleRepository.findByRole("USER"))));
		user.setPassword(bCryptPasswordEncoder.encode("Test@123"));
//		user.setCreated();
//		user.setUpdated();
		staff.setUser(user);
//		staff.setCreated(new Date());
//		staff.setUpdated(new Date());
		staffrepository.save(staff);
	}

	@Override
	public void saveDuration(Duration duration) {
		durationRepository.save(duration);
	}
	
	@Override
	public void saveMember(Membership membership) {
		membershipRepository.save(membership);
	}
	
	@Override
	public void saveCategory(Category category) {
		categoryRepository.save(category);
	}
	
	@Override
	public void saveService(ServiceDetails serviceDetails) {
		com.springbootexample.model.Service service = new com.springbootexample.model.Service();
		service.setCategory(serviceDetails.getCatId());
		service.setDuration(serviceDetails.getDuration());
		service.setGender(serviceDetails.getAvailability());
		service.setName(serviceDetails.getServiceName());
		service.setPrice(serviceDetails.getPrice());
		Set<LoyaltyPoints> loyaltyPoints = new HashSet<LoyaltyPoints>();
		String[] memberIds = serviceDetails.getMembership().split(",");
		String[] points = serviceDetails.getPoints().split(",");
		if(!StringUtils.isEmpty(memberIds[0])) {
			for(int i = 0; i < memberIds.length; i++) {
				LoyaltyPoints loyaltyPoint = new LoyaltyPoints();
				loyaltyPoint.setMembership(membershipRepository.findById(Long.valueOf(memberIds[i])));
				loyaltyPoint.setPoints(Double.valueOf(points[i]));
				loyaltyPoints.add(loyaltyPoint);
			}
		}
		service.setLoyaltyPoints(loyaltyPoints);
		service.setStaffList(serviceDetails.getStaff());
		serviceRepository.save(service);
	}
}
