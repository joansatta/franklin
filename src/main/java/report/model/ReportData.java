package report.model;

public class ReportData {

	private String groupId;
	private String groupName;
	private String groupDescription;
	private String yearWeek;
	private String fullDate;
	private String kpiValue;
	private String responseRate;

	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupDescription() {
		return groupDescription;
	}
	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}
	public String getYearWeek() {
		return yearWeek;
	}
	public void setYearWeek(String yearWeek) {
		this.yearWeek = yearWeek;
	}
	public String getFullDate() {
		return fullDate;
	}
	public void setFullDate(String fullDate) {
		this.fullDate = fullDate;
	}
	
	public String getKpiValue(){
		return kpiValue;
	}

	public String getKpiFormattedValue() {
		return getFormattedValue(kpiValue);
	}
	
	public String getResponseRateFormattedValue(){
		return getFormattedValue(responseRate);
	}
	
	private String getFormattedValue(String value) {
		try{
			Double numericKpi = Double.valueOf(value);
			numericKpi = numericKpi*100;
			return String.valueOf(numericKpi).split("\\.")[0]+"%";
		} catch (Exception e){
			return "";
		}
	}

	public void setKpiValue(String kpiValue) {
		this.kpiValue = kpiValue;
	}
	public String getResponseRate() {
		return responseRate;
	}
	public void setResponseRate(String responseRate) {
		this.responseRate = responseRate;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getYear(){
		return getFullDate()!=null?getFullDate().split("\\-")[0]:"";
	}
	public String getWeek(){
		return getYearWeek()!=null?getYearWeek().replace(getYear(),""):"";
	}




}
