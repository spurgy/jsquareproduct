package com.springbootexample.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springbootexample.model.Category;
import com.springbootexample.model.Duration;
import com.springbootexample.model.Membership;
import com.springbootexample.model.Service;
import com.springbootexample.model.Staff;
import com.springbootexample.model.User;
import com.springbootexample.services.AjaxService;
import com.springbootexample.services.UserService;

@Controller
@RequestMapping(value = "/util")
public class AjaxController {
	
	@Autowired
	private AjaxService ajaxservice;
	
	@Autowired
	private UserService userService;
	
	/*@RequestMapping(value = "/staff")
	@ResponseBody
    public String handleStaffRequest(HttpServletRequest request, Model model,@ModelAttribute StaffDetails staffDetails) {
		model.addAttribute("view", "staff");
		if(request.getMethod().matches("GET")) {
	        return "base";
		}
		adminService.saveStaffDetails(staffDetails);
		return "redirect:/admin/staff";
    }*/
	
	@RequestMapping(value = "/getUsernameRole")
	@ResponseBody
    public Map<String,String> getUsernameRole() {
		Map<String, String> filenames = userService.getUserNameRoleTopHeader();
		System.out.println("getFileName");
        return filenames;
    }
	
	@RequestMapping(value = "/getDesignationsMap")
	@ResponseBody
    public Map<Integer,String> getDesignationsMap() {
		Map<Integer, String> designations = ajaxservice.getAllDesignations();
        return designations;
    }
	
	@RequestMapping(value = "/getDurationMap")
	@ResponseBody
    public Map<Integer,String> durations() {
		Map<Integer, String> durations = ajaxservice.getAllDurations();
        return durations;
    }
	
	@RequestMapping(value = "/getDurationList")
	@ResponseBody
    public List<Duration> getDurationDataTable() {
		return ajaxservice.getAllDuration();
    }
	
	@RequestMapping(value = "/getStaffList")
	@ResponseBody
    public List<Staff> getStaffDataTable() {
		return ajaxservice.getAllStaffList();
    }
	
	@RequestMapping(value = "/getStaffMap")
	@ResponseBody
    public Map<Integer, String> getStaffDataMap() {
		return ajaxservice.getAllStaffMap();
    }
	
	@RequestMapping(value = "/getMembershipMap")
	@ResponseBody
    public Map<Integer, String> getMembershipMap() {
		return ajaxservice.getMembershipMap();
    }
	
	@RequestMapping(value = "/getMembershipList")
	@ResponseBody
    public List<Membership> Memberships() {
		return ajaxservice.getAllMembership();
    }
	
	@RequestMapping(value = "/getCategoryList")
	@ResponseBody
    public List<Category> getCategoryList() {
		return ajaxservice.getCategoryList();
    }
	
	@RequestMapping(value = "/getCatServicesList")
	@ResponseBody
    public List<Service> getCatServicesList() {
		return ajaxservice.getCatServicesList();
    }
	
	@RequestMapping(value = "/getCategoryMap")
	@ResponseBody
    public Map<Long, String> categories() {
		return ajaxservice.getCategoryMap();
    }
	
	@RequestMapping(value = "/getGenderMap")
	@ResponseBody
    public Map<Integer, String> genders() {
		return ajaxservice.getGenderMap();
    }
	
	@RequestMapping(value = "/getServiceMap")
	@ResponseBody
    public Map<Integer, String> services() {
		return ajaxservice.getServiceMap();
    }
	
	@RequestMapping(value = "/checkEmail")
	@ResponseBody
    public Map<String, Integer> checkEmail(@RequestParam(value="email", required=false) String email) {
		Map<String, Integer> status = new HashMap<String, Integer>();
		User userExists = userService.findUserByEmail(email);
		if (userExists != null) {
			status.put("status", 1);
			return status;
		}
		status.put("status", 0);
		return status;
    }
}
