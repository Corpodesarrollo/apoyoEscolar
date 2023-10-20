package siges.institucion.beans;

public class GradoItemVO {
	
	
	private String graNombre;
	private long graCodigo;
	private long graMetodCodigo;
	private long totalEstM;
	private long totalEstF;
	
	private long totalEstGradoM;
	private long totalEstGradoF;

	public long getGraCodigo() {
		return graCodigo;
	}
	public void setGraCodigo(long graCodigo) {
		this.graCodigo = graCodigo;
	}
	public String getGraNombre() {
		return graNombre;
	}
	public void setGraNombre(String graNombre) {
		this.graNombre = graNombre;
	}
	public long getTotalEstF() {
		return totalEstF;
	}
	public void setTotalEstF(long totalEstF) {
		this.totalEstF = totalEstF;
	}
	public long getTotalEstM() {
		return totalEstM;
	}
	public void setTotalEstM(long totalEstM) {
		this.totalEstM = totalEstM;
	}
	public GradoItemVO(long graCodigo, String graNombre,int graMetodCodigo) {
		super();
		this.graNombre = graNombre;
		this.graCodigo = graCodigo;
		this.graMetodCodigo= graMetodCodigo;
	}
	public long getGraMetodCodigo() {
		return graMetodCodigo;
	}
	public void setGraMetodCodigo(long graMetodCodigo) {
		this.graMetodCodigo = graMetodCodigo;
	}
	public long getTotalEstGradoF() {
		return totalEstGradoF;
	}
	public void setTotalEstGradoF(long totalEstGradoF) {
		this.totalEstGradoF = totalEstGradoF;
	}
	public long getTotalEstGradoM() {
		return totalEstGradoM;
	}
	public void setTotalEstGradoM(long totalEstGradoM) {
		this.totalEstGradoM = totalEstGradoM;
	}
}
