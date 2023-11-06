package integraciones.api.reportes.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * 03-07-2023
 * @author joslopez
 * @version 1.0
 *
 */
public class ReporteParametroDto implements Serializable{
	
	private static final long serialVersionUID = 1L;

    private Map<String, Object> params;

    private Integer topic;
    private String template;
    private String format;
    private String fileName;
    private Integer reportId;    
    private String modulo;
	private String path;
	private String nombrePDF;
	private String archivoSalida;
	private String nombreZIP;
	private int zise;
	private String list;
	private String bolDAO;
	private String reporteVO;
	private String reporte;
	private long consecutivoConsultaExterna;
	private String context;
	
	//datos para notificaciones
	private long sedeId;
	private long jornadaId;
	private long institucionId; 
	private String institucion;
	
		
	public long getSedeId() {
		return sedeId;
	}

	public void setSedeId(long sedeId) {
		this.sedeId = sedeId;
	}

	public long getJornadaId() {
		return jornadaId;
	}

	public void setJornadaId(long jornadaId) {
		this.jornadaId = jornadaId;
	}

	public long getInstitucionId() {
		return institucionId;
	}

	public void setInstitucionId(long institucionId) {
		this.institucionId = institucionId;
	}

	public String getInstitucion() {
		return institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	public String getModulo() {
		return modulo;
	}

	public void setModulo(String modulo) {
		this.modulo = modulo;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getNombrePDF() {
		return nombrePDF;
	}

	public void setNombrePDF(String nombrePDF) {
		this.nombrePDF = nombrePDF;
	}

	public String getArchivoSalida() {
		return archivoSalida;
	}

	public void setArchivoSalida(String archivoSalida) {
		this.archivoSalida = archivoSalida;
	}

	public String getNombreZIP() {
		return nombreZIP;
	}

	public void setNombreZIP(String nombreZIP) {
		this.nombreZIP = nombreZIP;
	}

	public int getZise() {
		return zise;
	}

	public void setZise(int zise) {
		this.zise = zise;
	}

	public String getList() {
		return list;
	}

	public void setList(String list) {
		this.list = list;
	}

	public String getBolDAO() {
		return bolDAO;
	}

	public void setBolDAO(String bolDAO) {
		this.bolDAO = bolDAO;
	}

	public String getReporteVO() {
		return reporteVO;
	}

	public void setReporteVO(String reporteVO) {
		this.reporteVO = reporteVO;
	}

	public String getReporte() {
		return reporte;
	}

	public void setReporte(String reporte) {
		this.reporte = reporte;
	}

	public long getConsecutivoConsultaExterna() {
		return consecutivoConsultaExterna;
	}

	public void setConsecutivoConsultaExterna(long consecutivoConsultaExterna) {
		this.consecutivoConsultaExterna = consecutivoConsultaExterna;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getReportId() {
		return reportId;
	}

	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public Integer getTopic() {
		return topic;
	}

	public void setTopic(Integer topic) {
		this.topic = topic;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}    
    
}
