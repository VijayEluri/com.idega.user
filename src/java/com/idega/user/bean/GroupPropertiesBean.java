package com.idega.user.bean;

import java.util.List;

public class GroupPropertiesBean {
	
	private String server = null;
	private String login = null;
	private String password = null;
	
	private List<String> groupsIds = null;
	
	private boolean showName = true;
	private boolean showHomePage = false;
	private boolean showDescription = false;
	private boolean showExtraInfo = false;
	private boolean showShortName = false;
	private boolean showPhone = false;
	private boolean showFax = false;
	private boolean showEmails = false;
	private boolean showAddress = false;
	private boolean showEmptyFields = true;
	
	public List<String> getGroupsIds() {
		return groupsIds;
	}
	public void setGroupsIds(List<String> groupsIds) {
		this.groupsIds = groupsIds;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public boolean isShowAddress() {
		return showAddress;
	}
	public void setShowAddress(boolean showAddress) {
		this.showAddress = showAddress;
	}
	public boolean isShowDescription() {
		return showDescription;
	}
	public void setShowDescription(boolean showDescription) {
		this.showDescription = showDescription;
	}
	public boolean isShowEmails() {
		return showEmails;
	}
	public void setShowEmails(boolean showEmails) {
		this.showEmails = showEmails;
	}
	public boolean isShowEmptyFields() {
		return showEmptyFields;
	}
	public void setShowEmptyFields(boolean showEmptyFields) {
		this.showEmptyFields = showEmptyFields;
	}
	public boolean isShowExtraInfo() {
		return showExtraInfo;
	}
	public void setShowExtraInfo(boolean showExtraInfo) {
		this.showExtraInfo = showExtraInfo;
	}
	public boolean isShowFax() {
		return showFax;
	}
	public void setShowFax(boolean showFax) {
		this.showFax = showFax;
	}
	public boolean isShowHomePage() {
		return showHomePage;
	}
	public void setShowHomePage(boolean showHomePage) {
		this.showHomePage = showHomePage;
	}
	public boolean isShowName() {
		return showName;
	}
	public void setShowName(boolean showName) {
		this.showName = showName;
	}
	public boolean isShowPhone() {
		return showPhone;
	}
	public void setShowPhone(boolean showPhone) {
		this.showPhone = showPhone;
	}
	public boolean isShowShortName() {
		return showShortName;
	}
	public void setShowShortName(boolean showShortName) {
		this.showShortName = showShortName;
	}
}
