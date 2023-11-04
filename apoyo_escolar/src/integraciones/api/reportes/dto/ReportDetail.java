package integraciones.api.reportes.dto;

import java.io.Serializable;
/**
 * 
 * @author joslopez
 *
 */
public class ReportDetail implements Serializable{
	/**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private byte[] reportGenerated;
    private String reportBase64Generated;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public byte[] getReportGenerated() {
		return reportGenerated;
	}
	public void setReportGenerated(byte[] reportGenerated) {
		this.reportGenerated = reportGenerated;
	}
	public String getReportBase64Generated() {
		return reportBase64Generated;
	}
	public void setReportBase64Generated(String reportBase64Generated) {
		this.reportBase64Generated = reportBase64Generated;
	}
    
    
}
