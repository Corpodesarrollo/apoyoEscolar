/**
 * 
 */
package siges.gestionAdministrativa.boletinPublico.vo;


/**
 * 28/04/2009 
 * @author Athenea TA
 * @version 1.1
 */
public class PlantillaBoletionPubVO  {

	
	private int cmd;
	private int tipo;
	private long plaboltipodoc;
	private String plabolnumdoc;
	private String plabolnomb;
	private long plabolinst;
	private String plabolinstResolucion;
	private String plabolinstDane;
	private String plabolinstBNomb;
	private long plabolsede;
	private String plabolsedeNomb;
	private long plaboljornd;
	private String plaboljorndNomb="nada";
	private long plabolmetodo;
	private long plabolgrado = -99;
	private String plabolgradoNomb;
	private long plabolgrupo = -1;
	private String plabolgrupoNomb;
	private long plabolperido;
	private String plabolperidoNomb;
	private int plabolperidoNum;
	private String plabolcoordNom;
	private long plabolvigencia;
	private String plabolconvmen;
	private String plabolconinst;
	private String filRutaBase;
	private String filUsuario;
	private long plabolcodigoest;
	private long plabolconsecRepBol;
	private long plabolniveleval;
	private String plabolarchivozip = "";
	
	

	
	public long getPlabolgrado() {
		return plabolgrado;
	}
	public void setPlabolgrado(long plabolgrado) {
		this.plabolgrado = plabolgrado;
	}
	public long getPlabolgrupo() {
		return plabolgrupo;
	}
	public void setPlabolgrupo(long plabolgrupo) {
		this.plabolgrupo = plabolgrupo;
	}
	public long getPlaboljornd() {
		return plaboljornd;
	}
	public void setPlaboljornd(long plaboljornd) {
		this.plaboljornd = plaboljornd;
	}
	public long getPlabolmetodo() {
		return plabolmetodo;
	}
	public void setPlabolmetodo(long plabolmetodo) {
		this.plabolmetodo = plabolmetodo;
	}
	public String getPlabolnomb() {
		return plabolnomb;
	}
	public void setPlabolnomb(String plabolnomb) {
		this.plabolnomb = plabolnomb;
	}
	public String getPlabolnumdoc() {
		return plabolnumdoc;
	}
	public void setPlabolnumdoc(String plabolnumdoc) {
		this.plabolnumdoc = plabolnumdoc;
	}
	public long getPlabolperido() {
		return plabolperido;
	}
	public void setPlabolperido(long plabolperido) {
		this.plabolperido = plabolperido;
	}
	public long getPlabolsede() {
		return plabolsede;
	}
	public void setPlabolsede(long plabolsede) {
		this.plabolsede = plabolsede;
	}
	public long getPlaboltipodoc() {
		return plaboltipodoc;
	}
	public void setPlaboltipodoc(long plaboltipodoc) {
		this.plaboltipodoc = plaboltipodoc;
	}
	public long getPlabolinst() {
		return plabolinst;
	}
	public void setPlabolinst(long plabolinst) {
		this.plabolinst = plabolinst;
	}
 
	public long getPlabolvigencia() {
		return plabolvigencia;
	}
	public void setPlabolvigencia(long plabolvigencia) {
		this.plabolvigencia = plabolvigencia;
	}
	public String getPlabolinstBNomb() {
		return plabolinstBNomb;
	}
	public void setPlabolinstBNomb(String plabolinstBNomb) {
		this.plabolinstBNomb = plabolinstBNomb;
	}
	public String getPlabolinstResolucion() {
		return plabolinstResolucion;
	}
	public void setPlabolinstResolucion(String plabolinstResolucion) {
		this.plabolinstResolucion = plabolinstResolucion;
	}
	public String getPlabolsedeNomb() {
		return plabolsedeNomb;
	}
	public void setPlabolsedeNomb(String plabolsedeNomb) {
		this.plabolsedeNomb = plabolsedeNomb;
	}
 
	public String getFilRutaBase() {
		return filRutaBase;
	}
	public void setFilRutaBase(String filRutaBase) {
		this.filRutaBase = filRutaBase;
	}
	public String getFilUsuario() {
		return filUsuario;
	}
	public void setFilUsuario(String filUsuario) {
		this.filUsuario = filUsuario;
	}
	public String getPlabolperidoNomb() {
		return plabolperidoNomb;
	}
	public void setPlabolperidoNomb(String plabolperidoNomb) {
		this.plabolperidoNomb = plabolperidoNomb;
	}
	public int getPlabolperidoNum() {
		return plabolperidoNum;
	}
	public void setPlabolperidoNum(int plabolperidoNum) {
		this.plabolperidoNum = plabolperidoNum;
	}
	public String getPlabolinstDane() {
		return plabolinstDane;
	}
	public void setPlabolinstDane(String plabolinstDane) {
		this.plabolinstDane = plabolinstDane;
	}
	public String getPlabolgradoNomb() {
		return plabolgradoNomb;
	}
	public void setPlabolgradoNomb(String plabolgradoNomb) {
		this.plabolgradoNomb = plabolgradoNomb;
	}
	public String getPlaboljorndNomb() {
		return plaboljorndNomb;
	}
	public void setPlaboljorndNomb(String plaboljorndNomb__) {
		System.out.println("--- plaboljorndNomb__ -- " + plaboljorndNomb__);
		this.plaboljorndNomb = plaboljorndNomb__;
	}
	public String getPlabolgrupoNomb() {
		return plabolgrupoNomb;
	}
	public void setPlabolgrupoNomb(String plabolgrupoNomb) {
		this.plabolgrupoNomb = plabolgrupoNomb;
	}
	public String getPlabolconvmen() {
		return plabolconvmen;
	}
	public void setPlabolconvmen(String plabolconvmen) {
		this.plabolconvmen = plabolconvmen;
	}
	public String getPlabolconinst() {
		return plabolconinst;
	}
	public void setPlabolconinst(String plabolconinst) {
		this.plabolconinst = plabolconinst;
	}
	public String getPlabolcoordNom() {
		return plabolcoordNom;
	}
	public void setPlabolcoordNom(String plabolcoordNom) {
		this.plabolcoordNom = plabolcoordNom;
	}
	public long getPlabolcodigoest() {
		return plabolcodigoest;
	}
	public void setPlabolcodigoest(long plabolcodigoest) {
		this.plabolcodigoest = plabolcodigoest;
	}
	public int getCmd() {
		return cmd;
	}
	public void setCmd(int cmd) {
		this.cmd = cmd;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public long getPlabolconsecRepBol() {
		return plabolconsecRepBol;
	}
	public void setPlabolconsecRepBol(long plabolconsecRepBol) {
		this.plabolconsecRepBol = plabolconsecRepBol;
	}
	public long getPlabolniveleval() {
		return plabolniveleval;
	}
	public void setPlabolniveleval(long plabolniveleval) {
		this.plabolniveleval = plabolniveleval;
	}
	public String getPlabolarchivozip() {
		return plabolarchivozip;
	}
	public void setPlabolarchivozip(String plabolarchivozip) {
		this.plabolarchivozip = plabolarchivozip;
	}
 
 
 
 
	 
}
