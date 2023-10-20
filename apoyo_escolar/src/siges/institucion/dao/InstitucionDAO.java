package siges.institucion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ResourceBundle;
import siges.dao.Cursor;
import siges.dao.Dao;
import siges.dao.OperacionesGenerales;
import siges.exceptions.InternalErrorException;
import siges.institucion.beans.*;

public class InstitucionDAO extends Dao {

    public String sentencia;

    public String mensaje;

    public String buscar;

    private java.text.SimpleDateFormat sdf;

    private ResourceBundle rb;

    public InstitucionDAO(Cursor cur) {
        super(cur);
        sentencia = null;
        mensaje = "";
        sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        rb = ResourceBundle.getBundle("siges.institucion.bundle.institucion");
    }

    public String getDane(String id) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet r = null;
        String dane = null;
        int posicion = 1; //posicion inicial de llenado del preparedstatement
        try {
            cn = cursor.getConnection();
            pst = cn.prepareStatement(rb.getString("dane"));
            pst.clearParameters();
            if (id.equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setLong(posicion++, Long.parseLong(id.trim()));
            r = pst.executeQuery();
            if (r.next()) {
                dane = r.getString(1);
            }
            return dane;
        } catch (InternalErrorException in) {
            setMensaje("NO se puede estabecer conexinn con la base de datos: ");
            return null;
        } catch (SQLException sqle) {
            setMensaje("Error intentando asignar colegio. Posible problema: ");
            switch (sqle.getErrorCode()) {
            default:
                setMensaje(sqle.getMessage().replace('\'', '`').replace('"',
                        'n'));
            }
            return dane;
        } finally {
            try {
                OperacionesGenerales.closeResultSet(r);
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            } catch (InternalErrorException inte) {
            }
        }
    }

    /**
     * Funcinn: Asignar Institucion <br>
     * 
     * @param String
     *            id
     * @param String
     *            id2
     * @return boolean
     */
    public Institucion asignarInstitucion(String id, String id2) {
        Institucion ins = null;
        Connection cn = null;
        PreparedStatement pst = null, pst2 = null, pst3 = null;
        ResultSet r = null;
        int posicion = 1; //posicion inicial de llenado del preparedstatement
        try {
            cn = cursor.getConnection();
            pst = cn.prepareStatement(rb.getString("InstitucionAsignar"));
            pst.clearParameters();
            if (id.equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setLong(posicion++, Long.parseLong(id.trim()));
            if (id2.equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setLong(posicion++, Long.parseLong(id2.trim()));
            r = pst.executeQuery();
            if (r.next()) {
                ins = new Institucion();
                int i = 1;
                ins.setInscodigo(r.getString(i++));
                ins.setInscoddane(r.getString(i++));
                ins.setInscoddaneanterior(r.getString(i++));
                ins.setInscoddepto(r.getString(i++));
                ins.setInscodmun(r.getString(i++));
                ins.setInscodlocal(r.getString(i++));
                ins.setInsnombre(r.getString(i++));
                ins.setInssector(r.getString(i++));
                ins.setInszona(r.getString(i++));
                ins.setInscalendario(r.getString(i++));
                ins.setInscalendariootro(r.getString(i++));
                ins.setInsrectornombre(r.getString(i++));
                ins.setInsrectorcc(r.getString(i++));
                ins.setInsrectoranoposesion(r.getString(i++));
                ins.setInsrectortel(r.getString(i++));
                ins.setInsrectorcorreo(r.getString(i++));
                ins.setInsreplegalnombre(r.getString(i++));
                ins.setInsreplegalcc(r.getString(i++));
                ins.setInsreplegaltel(r.getString(i++));
                ins.setInsreplegalcorreo(r.getString(i++));
                ins.setInspaginaweb(r.getString(i++));
                ins.setInspropiedad(r.getString(i++));
                ins.setInsnumsedes(r.getString(i++));
                ins.setInsgenero(r.getString(i++));
                ins.setInssubsidio(r.getString(i++));
                ins.setInsdiscapacidad(r.getString(i++));
                ins.setInscapexcepcional(r.getString(i++));
                ins.setInsetnia(r.getString(i++));
                ins.setInsnucleocodigo(r.getString(i++));
                ins.setInshimno(r.getString(i++));
                ins.setInslema(r.getString(i++));
                ins.setInsbandera(r.getString(i++));
                ins.setInsescudo(r.getString(i++));
                ins.setInshistoria(r.getString(i++));
                ins.setInsidioma(r.getString(i++));
                ins.setInsasocprivada(r.getString(i++));
                ins.setInsasocprivadaotro(r.getString(i++));
                ins.setInsregimencostos(r.getString(i++));
                ins.setInstarifacostos(r.getString(i++));
                ins.setInsinformepei(r.getString(i++));
                ins.setInscodmetodologia(r.getString(i++));
                ins.setInsnivellogro(r.getString(i++));
                ins.setInsmetodologia(r.getString(i++));
                ins.setEstado("1");
                //System.out.println("termino de insertar institucion");
            }
        } catch (InternalErrorException in) {
            setMensaje("NO se puede estabecer conexinn con la base de datos: ");
            return null;
        } catch (SQLException sqle) {
            setMensaje("Error intentando asignar colegio. Posible problema: ");
            switch (sqle.getErrorCode()) {
            default:
                setMensaje(sqle.getMessage().replace('\'', '`').replace('"',
                        'n'));
            }
            return ins;
        } finally {
            try {
                OperacionesGenerales.closeResultSet(r);
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            } catch (InternalErrorException inte) {
            }
        }
        return ins;
    }

    /**
     * Funcinn: Asignar Jornada <br>
     * 
     * @param String
     *            id
     * @return boolean
     */
    public Jornada asignarJornada(String id) {
        Jornada j = null;
        Connection cn = null;
        PreparedStatement pst = null, pst2 = null, pst3 = null;
        ResultSet r = null;
        int posicion = 1; //posicion inicial de llenado del preparedstatement
        try {
            cn = cursor.getConnection();
            pst = cn.prepareStatement(rb.getString("JornadaAsignar"));
            pst.clearParameters();
            if (id.equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setLong(posicion++, Long.parseLong(id.trim()));
            r = pst.executeQuery();
            j = new Jornada();
            Collection c = new ArrayList();
            int i = 1;
            while (r.next()) {
                c.add(r.getString(i));
            }
            String[] m = new String[c.size()];
            Iterator iterator = c.iterator();
            i = 0;
            while (iterator.hasNext()) {
                m[i++] = (String) iterator.next();
            }
            j.setJorcodins(id);
            j.setJorcodigo(m);
            j.setEstado("1");
        } catch (InternalErrorException in) {
            setMensaje("NO se puede estabecer conexinn con la base de datos: ");
            return null;
        } catch (SQLException sqle) {
            setMensaje("Error intentando asignar colegio. Posible problema: ");
            switch (sqle.getErrorCode()) {
            default:
                setMensaje(sqle.getMessage().replace('\'', '`').replace('"',
                        'n'));
            }
            return j;
        } finally {
            try {
                OperacionesGenerales.closeResultSet(r);
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            } catch (InternalErrorException inte) {
            }
        }
        return j;
    }

    /**
     * Funcinn: Eliminar sede <br>
     * 
     * @param String
     *            id
     * @param String
     *            id2
     * @return boolean
     */
    public boolean eliminarSede(String id, String id2) {
        int posicion = 1;
        Connection cn = null;
        PreparedStatement pst = null, pst2 = null, pst3 = null;
        try {
            cn = cursor.getConnection();
            cn.setAutoCommit(false);
            pst = cn.prepareStatement(rb.getString("SedeEliminar"));
            pst.clearParameters();
            if (id.equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setLong(posicion++, Long.parseLong(id.trim()));
            if (id2.equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(id2.trim()));
            pst.executeUpdate();
            cn.commit();
            cn.setAutoCommit(true);
        } catch (InternalErrorException in) {
            setMensaje("NO se puede estabecer conexinn con la base de datos: ");
            return false;
        } catch (SQLException sqle) {
            try {
                cn.rollback();
            } catch (SQLException s) {
            }
            setMensaje("Error intentando ELIMINAR sede. Posible problema: ");
            switch (sqle.getErrorCode()) {
            default:
                setMensaje(sqle.getMessage().replace('\'', '`').replace('"',
                        'n'));
            }
            return false;
        } finally {
            try {
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            } catch (InternalErrorException inte) {
            }
        }
        return true;
    }

    /**
     * Funcinn: Asignar Sede Jornada <br>
     * 
     * @param Sede
     *            s
     * @param String
     *            id
     * @param String
     *            id2
     * @return boolean
     */
    public Sede asignarSedeJornada(Sede s, String id, String id2) {
        Connection cn = null;
        PreparedStatement pst = null, pst2 = null, pst3 = null;
        ResultSet r = null;
        int posicion = 1;
        try {
            cn = cursor.getConnection();
            int i = 1, filas = 0, j = 0;
            String[] a;
            posicion = 1;
            pst = cn.prepareStatement(rb.getString("SedeJornadaAsignar"));
            pst.clearParameters();
            //System.out.println("ASIGNAR JORNADAS
            // "+Integer.parseInt(id2.trim()));
            if (id.equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setLong(posicion++, Long.parseLong(id.trim()));
            if (id2.equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(id2.trim()));
            r = pst.executeQuery();
            while (r.next()) {
                filas++;
            }
            a = new String[filas];
            r = pst.executeQuery();
            while (r.next()) {
                a[j] = r.getString(3);
                j++;
            }
            if (s != null)
                s.setSedjorcodjor(a);
            //System.out.println("ASIGNo JORNADAS"+a.length);
            //ASIGNAR NIVELES POR JORNADA
            //System.out.println("ASIGNAR NIVELES POR JORNADA");
            i = 1;
            filas = 0;
            j = 0;
            posicion = 1; //posicion inicial de llenado del preparedstatement
            pst = cn.prepareStatement(rb.getString("SedeJornadaNivelAsignar"));
            pst.clearParameters();
            if (id.equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setLong(posicion++, Long.parseLong(id.trim()));
            if (id2.equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(id2.trim()));
            r = pst.executeQuery();
            while (r.next()) {
                filas++;
            }
            a = new String[filas];
            r = pst.executeQuery();
            while (r.next()) {
                a[j] = r.getString(3) + "|" + r.getString(4);
                j++;
            }
            if (s != null)
                s.setSedjornivcodnivel(a);
            //System.out.println("ASIGNo NIVELES POR JORNADA"+a.length);
        } catch (InternalErrorException in) {
            setMensaje("NO se puede estabecer conexinn con la base de datos: ");
            return null;
        } catch (SQLException sqle) {
            //System.out.println("errorsote"+sqle);
            setMensaje("Error intentando asignar sedeJornada. Posible problema: ");
            switch (sqle.getErrorCode()) {
            default:
                setMensaje(sqle.getMessage().replace('\'', '`').replace('"',
                        'n'));
            }
            return s;
        } finally {
            try {
                OperacionesGenerales.closeResultSet(r);
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            } catch (InternalErrorException inte) {
            }
        }
        return s;
    }

    /**
     * Funcinn: Asignar Sede <br>
     * 
     * @param String
     *            id
     * @param String
     *            id2
     * @param String
     *            id3
     * @param String
     *            id4
     * @param String
     *            id5
     * @return boolean
     */
    public Sede asignarSede(String id, String id2, String id3, String id4){
        Sede s = null;
        Connection cn = null;
        PreparedStatement pst = null, pst2 = null, pst3 = null;
        ResultSet r = null;
        int posicion = 1;
        long vig=0;
        try {
            cn = cursor.getConnection();
            pst = cn.prepareStatement(rb.getString("VigenciaSede"));
            r = pst.executeQuery();
            if (r.next()) {
                vig=r.getLong(1);
            }
            r.close();
            pst.close();
            pst = cn.prepareStatement(rb.getString("SedeAsignar"));
            pst.clearParameters();
            /* codinst codsede daneinst danesede codsede ano */
            if (id == null || id.equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setLong(posicion++, Long.parseLong(id.trim()));
            if (id2 == null || id2.equals("")) pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setLong(posicion++, Long.parseLong(id2.trim()));
            if (id3 == null || id3.equals("")) pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setLong(posicion++, Long.parseLong(id3.trim()));
            if (id4 == null || id4.equals("")) pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setLong(posicion++, Long.parseLong(id4.trim()));
            if (id2 == null || id2.equals("")) pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setString(posicion++, id2.trim());
            pst.setLong(posicion++,vig);
            r = pst.executeQuery();
            if (r.next()) {
                s = new Sede();
                int i = 1;
                s.setSedcodins(r.getString(i++));
                s.setSedcodigo(r.getString(i++));
                s.setSedcoddaneanterior(r.getString(i++));
                s.setSedtipo(r.getString(i++));
                s.setSednombre(r.getString(i++));
                s.setSedzona(r.getString(i++));
                s.setSedvereda(r.getString(i++));
                s.setSedbarrio(r.getString(i++));
                s.setSedresguardo(r.getString(i++));
                s.setSeddireccion(r.getString(i++));
                s.setSedtelefono(r.getString(i++));
                s.setSedfax(r.getString(i++));
                s.setSedcorreo(r.getString(i++));
                s.setSedagua(r.getString(i++));
                s.setSedluz(r.getString(i++));
                s.setSedtel(r.getString(i++));
                s.setSedalcantarillado(r.getString(i++));
                s.setSedgas(r.getString(i++));
                s.setSedinternet(r.getString(i++));
                s.setSedinternettipo(r.getString(i++));
                s.setSedinternetprov(r.getString(i++));
                s.setEstado("1");
            }
        } catch (InternalErrorException in) {
            setMensaje("NO se puede estabecer conexinn con la base de datos: ");
            return null;
        } catch (SQLException sqle) {
            //System.out.println("errorsote"+sqle);
            setMensaje("Error intentando asignar sede. Posible problema: ");
            switch (sqle.getErrorCode()) {
            default:
                setMensaje(sqle.getMessage().replace('\'', '`').replace('"',
                        'n'));
            }
            return s;
        } finally {
            try {
                OperacionesGenerales.closeResultSet(r);
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            } catch (InternalErrorException inte) {
            }
        }
        return s;
    }

    /**
     * Funcinn: Asignar Transporte <br>
     * 
     * @param String
     *            id
     * @param String
     *            id2
     * @return boolean
     */
    public Transporte asignarTransporte(String id, String id2) {
        Transporte t = null;
        Connection cn = null;
        PreparedStatement pst = null, pst2 = null, pst3 = null;
        ResultSet r = null;
        int posicion = 1;
        try {
            cn = cursor.getConnection();
            pst = cn.prepareStatement(rb.getString("TransporteAsignar"));
            pst.clearParameters();
            if (id.equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setLong(posicion++, Long.parseLong(id.trim()));
            if (id2.equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(id2.trim()));
            r = pst.executeQuery();
            if (r.next()) {
                t = new Transporte();
                int i = 1;
                t.setTracodjerar(r.getString(i++));
                t.setTracodruta(r.getString(i++));
                t.setTranombre(r.getString(i++));
                t.setTraconductor(r.getString(i++));
                t.setTracelular(r.getString(i++));
                t.setTracupos(r.getString(i++));
                t.setTraplaca(r.getString(i++));
                t.setTraciudad(r.getString(i++));
                t.setTradescripcion(r.getString(i++));
                t.setTracostocompleto(r.getString(i++));
                t.setTracostomedia(r.getString(i++));
                t.setEstado("1");
            }
        } catch (InternalErrorException in) {
            setMensaje("NO se puede estabecer conexinn con la base de datos: ");
            return null;
        } catch (SQLException sqle) {
            setMensaje("Error intentando asignar transporte. Posible problema: ");
            switch (sqle.getErrorCode()) {
            default:
                setMensaje(sqle.getMessage().replace('\'', '`').replace('"',
                        'n'));
            }
            return t;
        } finally {
            try {
                OperacionesGenerales.closeResultSet(r);
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            } catch (InternalErrorException inte) {
            }
        }
        return t;
    }

    /**
     * Funcinn: Asignar Cafeteria <br>
     * 
     * @param String
     *            id
     * @param String
     *            id2
     * @return boolean
     */
    public Cafeteria asignarCafeteria(String id, String id2) {
        Cafeteria c = null;
        Connection cn = null;
        PreparedStatement pst = null, pst2 = null, pst3 = null;
        ResultSet r = null;
        int posicion = 1;
        try {
            cn = cursor.getConnection();
            pst = cn.prepareStatement(rb.getString("CafeteriaAsignar"));
            pst.clearParameters();
            if (id.equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setLong(posicion++, Long.parseLong(id.trim()));
            if (id2.equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(id2.trim()));
            r = pst.executeQuery();
            if (r.next()) {
                c = new Cafeteria();
                int i = 1;
                c.setRescodjerar(r.getString(i++));
                c.setRestiposerv(r.getString(i++));
                c.setResdescripcion(r.getString(i++));
                c.setReshorario(r.getString(i++));
                c.setRescosto(r.getString(i++));
                c.setEstado("1");
            }
        } catch (InternalErrorException in) {
            setMensaje("NO se puede estabecer conexinn con la base de datos: ");
            return null;
        } catch (SQLException sqle) {
            setMensaje("Error intentando asignar cafeteria. Posible problema: ");
            switch (sqle.getErrorCode()) {
            default:
                setMensaje(sqle.getMessage().replace('\'', '`').replace('"',
                        'n'));
            }
            return c;
        } finally {
            try {
                OperacionesGenerales.closeResultSet(r);
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            } catch (InternalErrorException inte) {
            }
        }
        return c;
    }

    /**
     * Funcinn: Eliminar Cafeteria <br>
     * 
     * @param String
     *            id
     * @param String
     *            id2
     * @return boolean
     */
    public boolean eliminarCafeteria(String id, String id2) {
        int posicion = 1;
        Connection cn = null;
        PreparedStatement pst = null, pst2 = null, pst3 = null;
        try {
            cn = cursor.getConnection();
            cn.setAutoCommit(false);
            pst = cn.prepareStatement(rb.getString("CafeteriaEliminar"));
            pst.clearParameters();
            if (id.equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setLong(posicion++, Long.parseLong(id.trim()));
            if (id2.equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(id2.trim()));
            pst.executeUpdate();
            cn.commit();
            cn.setAutoCommit(true);
        } catch (InternalErrorException in) {
            setMensaje("NO se puede estabecer conexinn con la base de datos: ");
            return false;
        } catch (SQLException sqle) {
            try {
                cn.rollback();
            } catch (SQLException s) {
            }
            setMensaje("Error intentando eliminar Cafeteria. Posible problema: ");
            switch (sqle.getErrorCode()) {
            default:
                setMensaje(sqle.getMessage().replace('\'', '`').replace('"',
                        'n'));
            }
            return false;
        } finally {
            try {
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            } catch (InternalErrorException inte) {
            }
        }
        return true;
    }

    /**
     * Funcinn: Asignar Espacio <br>
     * 
     * @param String
     *            id
     * @param String
     *            id2
     * @param String
     *            id3
     * @return boolean
     */
    public Espacio asignarEspacio(String id, String id2, String id3) {
        Espacio e = null;
        Connection cn = null;
        PreparedStatement pst = null, pst2 = null, pst3 = null;
        ResultSet r = null;
        int posicion = 1; //posicion inicial de llenado del preparedstatement
        try {
            cn = cursor.getConnection();
            pst = cn.prepareStatement(rb.getString("EspacioAsignar"));
            pst.clearParameters();
            if (id.equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setLong(posicion++, Long.parseLong(id.trim()));
            if (id2.equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(id2.trim()));
            if (id3.equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setLong(posicion++, Integer.parseInt(id3.trim()));
            r = pst.executeQuery();
            if (r.next()) {
                e = new Espacio();
                int i = 1;
                e.setEspcodins(r.getString(i++));
                e.setEspcodsede(r.getString(i++));
                e.setEspcodigo(r.getString(i++));
                e.setEsptipo(r.getString(i++));
                e.setEspnombre(r.getString(i++));
                e.setEspcapacidad(r.getString(i++));
                e.setEsparea(r.getString(i++));
                e.setEsptipoocupante(r.getString(i++));
                e.setEspestado(r.getString(i++));
                e.setEspfuncion(r.getString(i++));
                e.setEspdescripcion(r.getString(i++));
                e.setEstado("1");
            }
            int i = 1, filas = 0, j = 0;
            String[] a;
            posicion = 1; //posicion inicial de llenado del preparedstatement

            pst = cn
                    .prepareStatement(rb.getString("SedeJornadaEspacioAsignar"));
            pst.clearParameters();
            if (id.equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setLong(posicion++, Long.parseLong(id.trim()));
            if (id2.equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(id2.trim()));
            if (id3.equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setLong(posicion++, Integer.parseInt(id3.trim()));
            r = pst.executeQuery();
            while (r.next()) {
                filas++;
            }
            a = new String[filas];
            r = pst.executeQuery();
            while (r.next()) {
                a[j] = r.getString(3);
                j++;
            }
            e.setSedjorespcodjor(a);
        } catch (InternalErrorException in) {
            setMensaje("NO se puede estabecer conexinn con la base de datos: ");
            return null;
        } catch (SQLException sqle) {
            setMensaje("Error intentando asignar espacio fisico. Posible problema: ");
            switch (sqle.getErrorCode()) {
            default:
                setMensaje(sqle.getMessage().replace('\'', '`').replace('"',
                        'n'));
            }
            return e;
        } finally {
            try {
                OperacionesGenerales.closeResultSet(r);
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            } catch (InternalErrorException inte) {
            }
        }
        return e;
    }

    /**
     * Funcinn: Eliminar Espacio <br>
     * 
     * @param String
     *            id
     * @param String
     *            id2
     * @param String
     *            id3
     * @return
     */
    public boolean eliminarEspacio(String id, String id2, String id3) {
        int posicion = 1; //posicion inicial de llenado del preparedstatement
        Connection cn = null;
        PreparedStatement pst = null, pst2 = null, pst3 = null;
        try {
        		long inst=Long.parseLong(id.trim());
						long sede=Long.parseLong(id2.trim());
						long cod=Long.parseLong(id3.trim());
            cn = cursor.getConnection();
            cn.setAutoCommit(false);
            pst = cn.prepareStatement(rb.getString("EspacioEliminar"));
            pst.clearParameters();
            pst.setLong(posicion++, inst);
            pst.setLong(posicion++, sede);
            pst.setLong(posicion++, cod);
            pst.executeUpdate();
            pst.close();
            pst = cn.prepareStatement(rb.getString("SedeJornadaEspacioEliminar"));
            pst.clearParameters();posicion = 1;
            pst.setLong(posicion++, inst);
            pst.setLong(posicion++, sede);
            pst.setLong(posicion++, cod);
            pst.executeUpdate();            
            cn.commit();
            cn.setAutoCommit(true);
        } catch (InternalErrorException in) {
            setMensaje("NO se puede estabecer conexinn con la base de datos: ");
            return false;
        } catch (SQLException sqle) {
        	sqle.printStackTrace();
            try { cn.rollback(); } catch (SQLException s) { }
            setMensaje("Error intentando eliminar espacio fisico. Posible problema: ");
            switch (sqle.getErrorCode()) {
            default:
                setMensaje(sqle.getMessage().replace('\'', '`').replace('"','n'));
            }
            return false;
        } finally {
            try {
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            } catch (InternalErrorException inte) {
            }
        }
        return true;
    }

    /**
     * Funcinn: Asignar Nivel <br>
     * 
     * @param String
     *            id
     * @return boolean
     */
    public Nivel asignarNivel(String id) {
        Nivel j = null;
        Connection cn = null;
        PreparedStatement pst = null, pst2 = null, pst3 = null;
        ResultSet r = null;
        int posicion = 1; //posicion inicial de llenado del preparedstatement
        try {
            cn = cursor.getConnection();
            pst = cn.prepareStatement(rb.getString("NivelAsignar"));
            pst.clearParameters();
            if (id.equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setLong(posicion++, Long.parseLong(id.trim()));
            r = pst.executeQuery();
            j = new Nivel();
            Collection c = new ArrayList();
            int i = 2;
            while (r.next()) {
                c.add(r.getString(i));
            }
            String[] m = new String[c.size()];
            Iterator iterator = c.iterator();
            i = 0;
            while (iterator.hasNext()) {
                m[i++] = (String) iterator.next();
            }
            j.setNivcodinst(id);
            j.setNivcodigo(m);
            j.setEstado("1");
        } catch (InternalErrorException in) {
            setMensaje("NO se puede estabecer conexinn con la base de datos: ");
            return null;
        } catch (SQLException sqle) {
            setMensaje("Error intentando asignar Nivel. Posible problema: ");
            switch (sqle.getErrorCode()) {
            default:
                setMensaje(sqle.getMessage().replace('\'', '`').replace('"',
                        'n'));
            }
            return j;
        } finally {
            try {
                OperacionesGenerales.closeResultSet(r);
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            } catch (InternalErrorException inte) {
            }
        }
        return j;
    }

    /**
     * Funcinn: Eliminar transporte <br>
     * 
     * @param String
     *            id
     * @param String
     *            id2
     * @return boolean
     */
    public boolean eliminarTransporte(String id, String id2) {
        int posicion = 1;
        Connection cn = null;
        PreparedStatement pst = null, pst2 = null, pst3 = null;
        try {
            cn = cursor.getConnection();
            cn.setAutoCommit(false);
            pst = cn.prepareStatement(rb.getString("TransporteEliminar"));
            pst.clearParameters();
            if (id.equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setLong(posicion++, Long.parseLong(id.trim()));
            if (id2.equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(id2.trim()));
            pst.executeUpdate();
            cn.commit();
            cn.setAutoCommit(true);
        } catch (InternalErrorException in) {
            setMensaje("NO se puede estabecer conexinn con la base de datos: ");
            return false;
        } catch (SQLException sqle) {
            try {
                cn.rollback();
            } catch (SQLException s) {
            }
            setMensaje("Error intentando eliminar transporte. Posible problema: ");
            switch (sqle.getErrorCode()) {
            default:
                setMensaje(sqle.getMessage().replace('\'', '`').replace('"',
                        'n'));
            }
            return false;
        } finally {
            try {
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            } catch (InternalErrorException inte) {
            }
        }
        return true;
    }


    /**
     * Funcinn: Eliminar Gobierno <br>
     * 
     * @param String
     *            id
     * @param String
     *            id2
     * @return boolean
     */
    public boolean eliminarGobierno(String id, String id2) {
        int posicion = 1;
        Connection cn = null;
        PreparedStatement pst = null, pst2 = null, pst3 = null;
        try {
            cn = cursor.getConnection();
            cn.setAutoCommit(false);
            pst = cn.prepareStatement(rb.getString("GobiernoEliminar"));
            pst.clearParameters();
            if (id.equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setLong(posicion++, Long.parseLong(id.trim()));
            if (id2.equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(id2.trim()));
            pst.executeUpdate();
            cn.commit();
            cn.setAutoCommit(true);
        } catch (InternalErrorException in) {
            setMensaje("NO se puede estabecer conexinn con la base de datos: ");
            return false;
        } catch (SQLException sqle) {
            try {
                cn.rollback();
            } catch (SQLException s) {
            }
            setMensaje("Error intentando eliminar Gobierno. Posible problema: ");
            switch (sqle.getErrorCode()) {
            default:
                setMensaje(sqle.getMessage().replace('\'', '`').replace('"',
                        'n'));
            }
            return false;
        } finally {
            try {
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            } catch (InternalErrorException inte) {
            }
        }
        return true;
    }

    /**
     * Funcinn: Comparar beans de Institucion <br>
     * 
     * @param Institucion
     *            i
     * @param Institucion
     *            i2
     * @return boolean
     */
    public boolean compararBeans(Institucion i, Institucion i2) {
        if (!i.getInscodigo().equals(i2.getInscodigo()))
            return false;
        if (!i.getInscoddane().equals(i2.getInscoddane()))
            return false;
        if (!i.getInscoddaneanterior().equals(i2.getInscoddaneanterior()))
            return false;
        if (!i.getInscoddepto().equals(i2.getInscoddepto()))
            return false;
        if (!i.getInscodlocal().equals(i2.getInscodlocal()))
            return false;
        if (!i.getInscodmun().equals(i2.getInscodmun()))
            return false;
        if (!i.getInsnombre().equals(i2.getInsnombre()))
            return false;
        if (!i.getInssector().equals(i2.getInssector()))
            return false;
        if (!i.getInszona().equals(i2.getInszona()))
            return false;
        if (!i.getInscalendario().equals(i2.getInscalendario()))
            return false;
        if (!i.getInscalendariootro().equals(i2.getInscalendariootro()))
            return false;
        return true;
    }

    /**
     * Funcinn: Comparar beans de Jornada <br>
     * 
     * @param Jornada
     *            j
     * @param Jornada
     *            j2
     * @return boolean
     */
    public boolean compararBeans(Jornada j, Jornada j2) {
        if (!j.getJorcodins().equals(j2.getJorcodins()))
            return false;
        if (j.getJorcodigo() != null) {
            if (j.getJorcodigo().length != j2.getJorcodigo().length)
                return false;
            for (int i = 0; i < j.getJorcodigo().length; i++) {
                if (!j.getJorcodigo()[i].equals(j2.getJorcodigo()[i]))
                    return false;
            }
        }
        return true;
    }

    /**
     * Funcinn: Comparar beans de Sede <br>
     * 
     * @param Sede
     *            s
     * @param Sede
     *            s2
     * @return boolean
     */
    public boolean compararBeans(Sede s, Sede s2) {
        if (!s.getSedcodins().equals(s2.getSedcodins()))
            return false;
        if (!s.getSedcodigo().equals(s2.getSedcodigo()))
            return false;
        if (!s.getSedcoddaneanterior().equals(s2.getSedcoddaneanterior()))
            return false;
        if (!s.getSedtipo().equals(s2.getSedtipo()))
            return false;
        if (!s.getSednombre().equals(s2.getSednombre()))
            return false;
        if (!s.getSedzona().equals(s2.getSedzona()))
            return false;
        if (!s.getSedvereda().equals(s2.getSedvereda()))
            return false;
        if (!s.getSedbarrio().equals(s2.getSedbarrio()))
            return false;
        if (!s.getSedresguardo().equals(s2.getSedresguardo()))
            return false;
        if (!s.getSeddireccion().equals(s2.getSeddireccion()))
            return false;
        if (!s.getSedtelefono().equals(s2.getSedtelefono()))
            return false;
        if (!s.getSedfax().equals(s2.getSedfax()))
            return false;
        if (!s.getSedcorreo().equals(s2.getSedcorreo()))
            return false;
        if (!s.getSedagua().equals(s2.getSedagua()))
            return false;
        if (!s.getSedluz().equals(s2.getSedluz()))
            return false;
        if (!s.getSedtel().equals(s2.getSedtel()))
            return false;
        if (!s.getSedalcantarillado().equals(s2.getSedalcantarillado()))
            return false;
        if (!s.getSedgas().equals(s2.getSedgas()))
            return false;
        if (!s.getSedinternet().equals(s2.getSedinternet()))
            return false;
        if (!s.getSedinternettipo().equals(s2.getSedinternettipo()))
            return false;
        if (!s.getSedinternetprov().equals(s2.getSedinternetprov()))
            return false;
        if (s.getSedjorcodjor() != null && s2.getSedjorcodjor() != null) {
            if (s.getSedjorcodjor().length != s2.getSedjorcodjor().length)
                return false;
            for (int i = 0; i < s.getSedjorcodjor().length; i++) {
                if (!s.getSedjorcodjor()[i].equals(s2.getSedjorcodjor()[i]))
                    return false;
            }
        }
        if (s.getSedjornivcodnivel() != null
                && s2.getSedjornivcodnivel() != null) {
            if (s.getSedjornivcodnivel().length != s2.getSedjornivcodnivel().length)
                return false;
            for (int i = 0; i < s.getSedjornivcodnivel().length; i++) {
                if (!s.getSedjornivcodnivel()[i].equals(s2
                        .getSedjornivcodnivel()[i]))
                    return false;
            }
        }
        return true;
    }

    /**
     * Funcinn: Comparar beans de Espacio <br>
     * 
     * @param Espacio
     *            e
     * @param Espacio
     *            e2
     * @return boolean
     */

    public boolean compararBeans(Espacio e, Espacio e2) {
        if (!e.getEspcodins().equals(e2.getEspcodins()))
            return false;
        if (!e.getEspcodsede().equals(e2.getEspcodsede()))
            return false;
        if (!e.getEspcodigo().equals(e2.getEspcodigo()))
            return false;
        if (!e.getEsptipo().equals(e2.getEsptipo()))
            return false;
        if (!e.getEspnombre().equals(e2.getEspnombre()))
            return false;
        if (!e.getEspcapacidad().equals(e2.getEspcapacidad()))
            return false;
        if (!e.getEsparea().equals(e2.getEsparea()))
            return false;
        if (!e.getEsptipoocupante().equals(e2.getEsptipoocupante()))
            return false;
        if (!e.getEspestado().equals(e2.getEspestado()))
            return false;
        if (!e.getEspfuncion().equals(e2.getEspfuncion()))
            return false;
        if (!e.getEspdescripcion().equals(e2.getEspdescripcion()))
            return false;
        if (e.getSedjorespcodjor() != null && e2.getSedjorespcodjor() != null) {
            if (e.getSedjorespcodjor().length != e2.getSedjorespcodjor().length)
                return false;
            for (int i = 0; i < e.getSedjorespcodjor().length; i++) {
                if (!e.getSedjorespcodjor()[i]
                        .equals(e2.getSedjorespcodjor()[i]))
                    return false;
            }
        }
        return true;
    }

    /**
     * Funcinn: Comparar beans de Nivel <br>
     * 
     * @param Nivel
     *            n
     * @param Nivel
     *            n2
     * @return boolean
     */
    public boolean compararBeans(Nivel n, Nivel n2) {
        if (!n.getNivcodinst().equals(n2.getNivcodinst()))
            return false;
        if (n.getNivcodigo() != null && n2.getNivcodigo() != null) {
            if (n.getNivcodigo().length != n2.getNivcodigo().length)
                return false;
            for (int i = 0; i < n.getNivcodigo().length; i++) {
                if (!n.getNivcodigo()[i].equals(n2.getNivcodigo()[i]))
                    return false;
            }
        }
        if (n.getNivnombre() != null && n2.getNivnombre() != null) {
            if (n.getNivnombre().length != n2.getNivnombre().length)
                return false;
            for (int i = 0; i < n.getNivnombre().length; i++) {
                if (!n.getNivnombre()[i].equals(n2.getNivnombre()[i]))
                    return false;
            }
        }
        if (n.getNivcodmetod() != null && n2.getNivcodmetod() != null) {
            if (n.getNivcodmetod().length != n2.getNivcodmetod().length)
                return false;
            for (int i = 0; i < n.getNivcodmetod().length; i++) {
                if (!n.getNivcodmetod()[i].equals(n2.getNivcodmetod()[i]))
                    return false;
            }
        }
        if (n.getNivorden() != null && n2.getNivorden() != null) {
            if (n.getNivorden().length != n2.getNivorden().length)
                return false;
            for (int i = 0; i < n.getNivorden().length; i++) {
                if (!n.getNivorden()[i].equals(n2.getNivorden()[i]))
                    return false;
            }
        }
        return true;
    }


    /**
     * Funcinn: Comparar beans de Transporte <br>
     * 
     * @param Transporte
     *            t
     * @param Transporte
     *            t2
     * @return boolean
     */

    public boolean compararBeans(Transporte t, Transporte t2) {
        if (!t.getTracodjerar().equals(t2.getTracodjerar()))
            return false;
        if (!t.getTracodruta().equals(t2.getTracodruta()))
            return false;
        if (!t.getTranombre().equals(t2.getTranombre()))
            return false;
        if (!t.getTraconductor().equals(t2.getTraconductor()))
            return false;
        if (!t.getTracelular().equals(t2.getTracelular()))
            return false;
        if (!t.getTracupos().equals(t2.getTracupos()))
            return false;
        if (!t.getTraplaca().equals(t2.getTraplaca()))
            return false;
        if (!t.getTraciudad().equals(t2.getTraciudad()))
            return false;
        if (!t.getTradescripcion().equals(t2.getTradescripcion()))
            return false;
        if (!t.getTracostocompleto().equals(t2.getTracostocompleto()))
            return false;
        if (!t.getTracostomedia().equals(t2.getTracostomedia()))
            return false;
        return true;
    }

    /**
     * Funcinn: Comparar beans de Cafeteria <br>
     * 
     * @param Cafeteria
     *            c
     * @param Cafeteria
     *            c2
     * @return boolean
     */
    public boolean compararBeans(Cafeteria c, Cafeteria c2) {
        if (!c.getRescodjerar().equals(c2.getRescodjerar()))
            return false;
        if (!c.getRestiposerv().equals(c2.getRestiposerv()))
            return false;
        if (!c.getResdescripcion().equals(c2.getResdescripcion()))
            return false;
        if (!c.getReshorario().equals(c2.getReshorario()))
            return false;
        if (!c.getRescosto().equals(c2.getRescosto()))
            return false;
        return true;
    }

    /**
     * Funcinn: Actualizar Institucinn <br>
     * 
     * @param Institucion
     *            i
     * @return boolean
     */
    public boolean actualizar(Institucion i) {
        int posicion = 1; //posicion inicial de llenado del preparedstatement
        Connection cn = null;
        PreparedStatement pst = null, pst2 = null, pst3 = null;
        try {
            cn = cursor.getConnection();
            cn.setAutoCommit(false);
            pst = cn.prepareStatement(rb.getString("InstitucionActualizar"));
            pst.clearParameters();
            //1
            if (i.getInscoddane().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setLong(posicion++, Long
                        .parseLong(i.getInscoddane().trim()));
            //2
            if (i.getInscoddaneanterior().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setLong(posicion++, Long.parseLong(i
                        .getInscoddaneanterior().trim()));
            //3
            if (i.getInscoddepto().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(i.getInscoddepto()
                        .trim()));
            //4
            if (i.getInscodmun().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer
                        .parseInt(i.getInscodmun().trim()));
            //5
            if (i.getInscodlocal().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(i.getInscodlocal()
                        .trim()));
            //6
            if (i.getInsnombre().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, i.getInsnombre().trim());
            //7
            if (i.getInssector().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer
                        .parseInt(i.getInssector().trim()));
            //8
            if (i.getInszona().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(i.getInszona().trim()));
            //9
            if (i.getInscalendario().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(i.getInscalendario()
                        .trim()));
            //10
            if (i.getInscalendariootro().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, i.getInscalendariootro().trim());
            //11
            if (i.getInsrectornombre().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, i.getInsrectornombre().trim());
            //12
            if (i.getInsrectorcc().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setLong(posicion++, Long.parseLong(i.getInsrectorcc()
                        .trim()));
            //13
            if (i.getInsrectoranoposesion().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(i
                        .getInsrectoranoposesion().trim()));
            //14
            if (i.getInsrectortel().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, i.getInsrectortel().trim());
            //15
            if (i.getInsrectorcorreo().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, i.getInsrectorcorreo().trim());
            //16
            if (i.getInsreplegalnombre().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, i.getInsreplegalnombre().trim());
            //17
            if (i.getInsreplegalcc().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setLong(posicion++, Long.parseLong(i.getInsreplegalcc()
                        .trim()));
            //18
            if (i.getInsreplegaltel().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, i.getInsreplegaltel().trim());
            //19
            if (i.getInsreplegalcorreo().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, i.getInsreplegalcorreo().trim());
            //20
            if (i.getInspaginaweb().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, i.getInspaginaweb().trim());
            //21
            if (i.getInspropiedad().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(i.getInspropiedad()
                        .trim()));
            //22
            if (i.getInsnumsedes().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(i.getInsnumsedes()
                        .trim()));
            //23
            if (i.getInsgenero().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer
                        .parseInt(i.getInsgenero().trim()));
            //24
            if (i.getInssubsidio().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, i.getInssubsidio().trim());
            //25
            if (i.getInsdiscapacidad().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(i.getInsdiscapacidad()
                        .trim()));
            //26
            if (i.getInscapexcepcional().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, i.getInscapexcepcional().trim());
            //27
            if (i.getInsetnia().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, i.getInsetnia().trim());
            //28
            if (i.getInsnucleocodigo().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setLong(posicion++, Long.parseLong(i.getInsnucleocodigo()
                        .trim()));
            //29
            if (i.getInsidioma().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer
                        .parseInt(i.getInsidioma().trim()));
            //30
            if (i.getInsasocprivada().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(i.getInsasocprivada()
                        .trim()));
            //31
            if (i.getInsasocprivadaotro().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, i.getInsasocprivadaotro().trim());
            //32
            if (i.getInsregimencostos().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(i.getInsregimencostos()
                        .trim()));
            //33
            if (i.getInstarifacostos().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(i.getInstarifacostos()
                        .trim()));
            //34
            if (i.getInsinformepei().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, i.getInsinformepei().trim());
            //35
            if (i.getInsmetodologia().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(i.getInsmetodologia()
                        .trim()));
            //36
            if (i.getInscodmetodologia().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(i
                        .getInscodmetodologia().trim()));
            //37
            if (i.getInsnivellogro().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(i.getInsnivellogro()
                        .trim()));
            //38
            if (i.getInscodigo().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst
                        .setLong(posicion++, Long.parseLong(i.getInscodigo()
                                .trim()));
            pst.executeUpdate();
            cn.commit();
            cn.setAutoCommit(true);
        } catch (InternalErrorException in) {
            setMensaje("NO se puede estabecer conexinn con la base de datos: ");
            return false;
        } catch (SQLException sqle) {
            try {
                cn.rollback();
            } catch (SQLException s) {
            }
            setMensaje("Error intentando ingresar información de institucion. Posible problema: ");
            switch (sqle.getErrorCode()) {
            default:
                setMensaje(sqle.getMessage().replace('\'', '`').replace('"',
                        'n'));
            }
            return false;
        } finally {
            try {
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            } catch (InternalErrorException inte) {
            }
        }
        return true;
    }

    /**
     * Funcinn: Actualizar jornada <br>
     * 
     * @param Jornada
     *            j
     * @return boolean
     */
    public boolean actualizar(Jornada j) {
        int posicion = 1; //posicion inicial de llenado del preparedstatement
        Connection cn = null;
        PreparedStatement pst = null, pst2 = null, pst3 = null;
        try {
            cn = cursor.getConnection();
            cn.setAutoCommit(false);
            pst = cn.prepareStatement(rb.getString("JornadaEliminar"));
            pst.clearParameters();
            if (j.getJorcodins().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst
                        .setLong(posicion++, Long.parseLong(j.getJorcodins()
                                .trim()));
            pst.executeUpdate();

            posicion = 1;
            pst = cn.prepareStatement(rb.getString("JornadaInsertar"));
            String a[] = j.getJorcodigo();
            if (a != null) {
                for (int i = 0; i < a.length; i++) {
                    posicion = 1;
                    pst.clearParameters();
                    if (j.getJorcodins().equals(""))
                        pst.setNull(posicion++, java.sql.Types.VARCHAR);
                    else
                        pst.setLong(posicion++, Long.parseLong(j.getJorcodins()
                                .trim()));
                    if (a[i] == null || a[i].equals(""))
                        pst.setNull(posicion++, java.sql.Types.VARCHAR);
                    else
                        pst.setInt(posicion++, Integer.parseInt(a[i].trim()));
                    pst.executeUpdate();
                }
            }
            cn.commit();
            cn.setAutoCommit(true);
        } catch (InternalErrorException in) {
            setMensaje("NO se puede estabecer conexinn con la base de datos: ");
            return false;
        } catch (SQLException sqle) {
            try {
                cn.rollback();
            } catch (SQLException s) {
            }
            setMensaje("Error intentando actualizar jornadas.("
                    + sqle.getErrorCode() + ") Posible problema: ");
            switch (sqle.getErrorCode()) {
            default:
                setMensaje(sqle.getMessage().replace('\'', '`').replace('"',
                        'n'));
            }
            return false;
        } finally {
            try {
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            } catch (InternalErrorException inte) {
            }
        }
        return true;
    }

    /**
     * Inserta los datos del bean en la tabla de informacion basica del egresado
     * 
     * @param Cursor
     *            cursor
     * @param ActualizarNuevoBasica
     *            basica
     * @param int
     *            tipo
     * @return boolean
     */

    public boolean actualizar(Nivel nivel) {
        int posicion = 1; //posicion inicial de llenado del preparedstatement
        Connection cn = null;
        PreparedStatement pst = null, pst2 = null, pst3 = null;
        try {
            cn = cursor.getConnection();
            cn.setAutoCommit(false);
            pst = cn.prepareStatement(rb.getString("NivelEliminar"));
            pst.clearParameters();
            if (nivel.getNivcodinst().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setLong(posicion++, Long.parseLong(nivel.getNivcodinst()
                        .trim()));
            pst.executeUpdate();

            posicion = 1;
            pst = cn.prepareStatement(rb.getString("NivelInsertar"));
            String a[] = nivel.getNivcodigo();
            String b[] = nivel.getNivnombre();
            String c[] = nivel.getNivcodmetod() != null ? nivel
                    .getNivcodmetod() : new String[a.length];
            String d[] = nivel.getNivorden();
            if (a != null) {
                for (int i = 0; i < a.length; i++) {
                    posicion = 1;
                    pst.clearParameters();
                    if (nivel.getNivcodinst().equals(""))
                        pst.setNull(posicion++, java.sql.Types.VARCHAR);
                    else
                        pst.setLong(posicion++, Long.parseLong(nivel
                                .getNivcodinst().trim()));
                    if (a[i] == null || a[i].equals(""))
                        pst.setNull(posicion++, java.sql.Types.VARCHAR);
                    else
                        pst.setInt(posicion++, Integer.parseInt(a[i].trim()));
                    if (b[i] == null || b[i].equals(""))
                        pst.setNull(posicion++, java.sql.Types.VARCHAR);
                    else
                        pst.setString(posicion++, b[i].trim());
                    if (c[i] == null || c[i].equals(""))
                        pst.setNull(posicion++, java.sql.Types.VARCHAR);
                    else
                        pst.setInt(posicion++, Integer.parseInt(c[i].trim()));
                    if (d[i] == null || d[i].equals(""))
                        pst.setNull(posicion++, java.sql.Types.VARCHAR);
                    else
                        pst.setInt(posicion++, Integer.parseInt(d[i].trim()));
                    pst.executeUpdate();
                }
            }
            cn.commit();
            cn.setAutoCommit(true);
            return true;
        } catch (InternalErrorException in) {
            setMensaje("NO se puede estabecer conexinn con la base de datos: ");
            return false;
        } catch (SQLException sqle) {
            try {
                cn.rollback();
            } catch (SQLException s) {
            }
            setMensaje("Error intentando actualizar niveles. Posible problema: ");
            switch (sqle.getErrorCode()) {
            default:
                setMensaje(sqle.getMessage().replace('\'', '`').replace('"',
                        'n'));
            }
            return false;
        } finally {
            try {
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            } catch (InternalErrorException inte) {
            }
        }
    }

    /**
     * Funcinn: Actualizar Sede <br>
     * 
     * @param Sede
     *            s
     * @return boolean
     */
    public boolean actualizar(Sede s) {
        int posicion = 1;
        Connection cn = null;
        PreparedStatement pst = null, pst2 = null, pst3 = null;
        try {
            cn = cursor.getConnection();
            cn.setAutoCommit(false);
            pst = cn.prepareStatement(rb.getString("SedeActualizar"));
            pst.clearParameters();
            if (s.getSedcodins().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst
                        .setLong(posicion++, Long.parseLong(s.getSedcodins()
                                .trim()));
            if (s.getSedcodigo().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer
                        .parseInt(s.getSedcodigo().trim()));
            if (s.getSedcoddaneanterior().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setLong(posicion++, Long.parseLong(s
                        .getSedcoddaneanterior().trim()));
            if (s.getSedtipo().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(s.getSedtipo().trim()));
            if (s.getSednombre().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, s.getSednombre().trim());
            if (s.getSedzona().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(s.getSedzona().trim()));
            if (s.getSedvereda().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, s.getSedvereda().trim());
            if (s.getSedbarrio().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, s.getSedbarrio().trim());
            if (s.getSedresguardo().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, s.getSedresguardo().trim());
            if (s.getSeddireccion().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, s.getSeddireccion().trim());
            if (s.getSedtelefono().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, s.getSedtelefono().trim());
            if (s.getSedfax().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, s.getSedfax().trim());
            if (s.getSedcorreo().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, s.getSedcorreo().trim());
            if (s.getSedagua().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(s.getSedagua().trim()));
            if (s.getSedluz().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(s.getSedluz().trim()));
            if (s.getSedtel().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(s.getSedtel().trim()));
            if (s.getSedalcantarillado().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(s
                        .getSedalcantarillado().trim()));
            if (s.getSedgas().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(s.getSedgas().trim()));
            if (s.getSedinternet().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(s.getSedinternet()
                        .trim()));
            if (s.getSedinternettipo().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(s.getSedinternettipo()
                        .trim()));
            if (s.getSedinternetprov().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, s.getSedinternetprov().trim());

            if (s.getSedcodins().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst
                        .setLong(posicion++, Long.parseLong(s.getSedcodins()
                                .trim()));
            if (s.getSedcodigo().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer
                        .parseInt(s.getSedcodigo().trim()));
            pst.executeUpdate();
            pst.close();
            posicion = 1;
            pst = cn.prepareStatement(rb.getString("SedeJornadaEliminar"));
            pst.clearParameters();
            if (s.getSedcodins().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst
                        .setLong(posicion++, Long.parseLong(s.getSedcodins()
                                .trim()));
            if (s.getSedcodigo().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer
                        .parseInt(s.getSedcodigo().trim()));
            pst.executeUpdate();
            pst.close();
            pst = cn.prepareStatement(rb.getString("SedeJornadaInsertar"));
            String[] a = s.getSedjorcodjor();
            if (a != null) {
                for (int i = 0; i < a.length; i++) {
                    posicion = 1;
                    pst.clearParameters();
                    if (s.getSedcodins().equals(""))
                        pst.setNull(posicion++, java.sql.Types.VARCHAR);
                    else
                        pst.setLong(posicion++, Long.parseLong(s.getSedcodins()
                                .trim()));
                    if (s.getSedcodigo().equals(""))
                        pst.setNull(posicion++, java.sql.Types.VARCHAR);
                    else
                        pst.setInt(posicion++, Integer.parseInt(s
                                .getSedcodigo().trim()));
                    if (a[i] == null || a[i].equals(""))
                        pst.setNull(posicion++, java.sql.Types.VARCHAR);
                    else
                        pst.setInt(posicion++, Integer.parseInt(a[i].trim()));
                    pst.executeUpdate();
                }
            }

            //CUESTION CON LAS NIVELES POR JORNADA ELIMINAR
            posicion = 1;
            pst.close();
            pst = cn.prepareStatement(rb.getString("SedeJornadaNivelEliminar"));
            pst.clearParameters();
            if (s.getSedcodins().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst
                        .setLong(posicion++, Long.parseLong(s.getSedcodins()
                                .trim()));
            if (s.getSedcodigo().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer
                        .parseInt(s.getSedcodigo().trim()));
            pst.executeUpdate();
            //OTRA CUESTION PARA INSERTAR
            pst.close();
            pst = cn.prepareStatement(rb.getString("SedeJornadaNivelInsertar"));
            a = s.getSedjornivcodnivel();
            String n1 = "", n2 = "";
            int aux;
            if (a != null) {
                for (int i = 0; i < a.length; i++) {
                    posicion = 1;
                    pst.clearParameters();
                    aux = a[i].lastIndexOf("|");
                    n1 = a[i].substring(0, aux);
                    //System.out.println("n1="+n1);
                    n2 = a[i].substring(aux + 1, a[i].length());
                    //System.out.println("n2"+n2);
                    if (s.getSedcodins().equals(""))
                        pst.setNull(posicion++, java.sql.Types.VARCHAR);
                    else
                        pst.setLong(posicion++, Long.parseLong(s.getSedcodins()
                                .trim()));
                    if (s.getSedcodigo().equals(""))
                        pst.setNull(posicion++, java.sql.Types.VARCHAR);
                    else
                        pst.setInt(posicion++, Integer.parseInt(s
                                .getSedcodigo().trim()));
                    if (n1 == null || n1.equals(""))
                        pst.setNull(posicion++, java.sql.Types.VARCHAR);
                    else
                        pst.setInt(posicion++, Integer.parseInt(n1.trim()));
                    if (n2 == null || n2.equals(""))
                        pst.setNull(posicion++, java.sql.Types.VARCHAR);
                    else
                        pst.setInt(posicion++, Integer.parseInt(n2.trim()));
                    pst.executeUpdate();
                }
            }
            cn.commit();
            cn.setAutoCommit(true);
        } catch (InternalErrorException in) {
            setMensaje("NO se puede estabecer conexinn con la base de datos: ");
            return false;
        } catch (SQLException sqle) {
            try {
                cn.rollback();
            } catch (SQLException se) {
            }
            setMensaje("Error intentando actualizar sede.("
                    + sqle.getErrorCode() + ") Posible problema: ");
            switch (sqle.getErrorCode()) {
            default:
                setMensaje(sqle.getMessage().replace('\'', '`').replace('"',
                        'n'));
            }
            return false;
        } finally {
            try {
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            } catch (InternalErrorException inte) {
            }
        }
        return true;
    }

    /**
     * Inserta los datos del bean en la tabla de informacion basica del egresado
     * 
     * @param Cursor
     *            cursor
     * @param ActualizarNuevoBasica
     *            basica
     * @param int
     *            tipo
     * @return boolean
     */

    public boolean actualizar(Espacio e) {
        int posicion = 1;
        Connection cn = null;
        PreparedStatement pst = null, pst2 = null, pst3 = null;
        try {
        		long inst=Long.parseLong(e.getEspcodins().trim());
        		long sede=Long.parseLong(e.getEspcodsede().trim());
        		long esp=Long.parseLong(e.getEspcodigo().trim());
            cn = cursor.getConnection();
            cn.setAutoCommit(false);
            pst = cn.prepareStatement(rb.getString("EspacioActualizar"));
            pst.clearParameters();
            pst.setLong(posicion++, inst);
            pst.setLong(posicion++, sede);
            pst.setLong(posicion++, esp);
            if (e.getEsptipo().equals("")) pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setInt(posicion++, Integer.parseInt(e.getEsptipo().trim()));
            if (e.getEspnombre().equals("")) pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setString(posicion++, e.getEspnombre().trim());
            if (e.getEspcapacidad().equals("")) pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setLong(posicion++, Long.parseLong(e.getEspcapacidad().trim()));
            if (e.getEsparea().equals(""))pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setLong(posicion++, Long.parseLong(e.getEsparea().trim()));
            if (e.getEsptipoocupante().equals("")) pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setInt(posicion++, Integer.parseInt(e.getEsptipoocupante().trim()));
            if (e.getEspestado().equals("")) pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setInt(posicion++, Integer.parseInt(e.getEspestado().trim()));
            if (e.getEspfuncion().equals("")) pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setString(posicion++, e.getEspfuncion().trim());
            if (e.getEspdescripcion().equals("")) pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setString(posicion++, e.getEspdescripcion().trim());
            pst.setLong(posicion++, inst);
            pst.setLong(posicion++, sede);
            pst.setLong(posicion++, esp);
            pst.executeUpdate();
            posicion = 1;
            pst.close();
            pst = cn.prepareStatement(rb.getString("SedeJornadaEspacioEliminar"));
            pst.clearParameters();
            pst.setLong(posicion++, inst);
            pst.setLong(posicion++, sede);
            pst.setLong(posicion++, esp);
            pst.executeUpdate();
            pst.close();
            pst = cn.prepareStatement(rb.getString("SedeJornadaEspacioInsertar"));
            String[] a = e.getSedjorespcodjor();
            if (a != null) {
                for (int i = 0; i < a.length; i++) {
                    posicion = 1;
                    pst.clearParameters();
                    pst.setLong(posicion++, inst);
                    pst.setLong(posicion++, sede);
                    pst.setLong(posicion++, esp);
                    if (a[i] == null || a[i].equals("")) pst.setNull(posicion++, java.sql.Types.VARCHAR);
                    else pst.setInt(posicion++, Integer.parseInt(a[i].trim()));
                    pst.executeUpdate();
                }
            }
            cn.commit();
            cn.setAutoCommit(true);
        } catch (InternalErrorException in) {
            setMensaje("NO se puede estabecer conexinn con la base de datos: ");
            return false;
        } catch (SQLException sqle) {
            try { cn.rollback(); } catch (SQLException s) { }
            setMensaje("Error intentando actualizar espacio fisico. Posible problema: ");
            switch (sqle.getErrorCode()) {
            	default: setMensaje(sqle.getMessage().replace('\'', '`').replace('"','n'));
            }
            return false;
        } finally {
            try {
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            } catch (InternalErrorException inte) {}
        }
        return true;
    }


    /**
     * Inserta los datos del bean en la tabla de informacion basica del egresado
     * 
     * @param Cursor
     *            cursor
     * @param ActualizarNuevoBasica
     *            basica
     * @param int
     *            tipo
     * @return boolean
     */
    public boolean actualizar(Transporte t) {
        int posicion = 1;
        Connection cn = null;
        PreparedStatement pst = null, pst2 = null, pst3 = null;
        try {
            cn = cursor.getConnection();
            cn.setAutoCommit(false);
            pst = cn.prepareStatement(rb.getString("TransporteActualizar"));
            pst.clearParameters();
            if (t.getTranombre().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, t.getTranombre().trim());
            if (t.getTraconductor().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, t.getTraconductor().trim());
            if (t.getTracelular().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, t.getTracelular().trim());
            if (t.getTracupos().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst
                        .setInt(posicion++, Integer.parseInt(t.getTracupos()
                                .trim()));
            if (t.getTraplaca().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, t.getTraplaca().trim());
            if (t.getTraciudad().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, t.getTraciudad().trim());
            if (t.getTradescripcion().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, t.getTradescripcion().trim());
            if (t.getTracostocompleto().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setLong(posicion++, Long.parseLong(t.getTracostocompleto()
                        .trim()));
            if (t.getTracostomedia().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setLong(posicion++, Long.parseLong(t.getTracostomedia()
                        .trim()));

            if (t.getTracodjerar().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setLong(posicion++, Long.parseLong(t.getTracodjerar()
                        .trim()));
            if (t.getTracodruta().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(t.getTracodruta()
                        .trim()));
            pst.executeUpdate();
            cn.commit();
            cn.setAutoCommit(true);
        } catch (InternalErrorException in) {
            setMensaje("NO se puede estabecer conexinn con la base de datos: ");
            return false;
        } catch (SQLException sqle) {
            try {
                cn.rollback();
            } catch (SQLException s) {
            }
            setMensaje("Error intentando actualizar gobierno. Posible problema: ");
            switch (sqle.getErrorCode()) {
            default:
                setMensaje(sqle.getMessage().replace('\'', '`').replace('"',
                        'n'));
            }
            return false;
        } finally {
            try {
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            } catch (InternalErrorException inte) {
            }
        }
        return true;
    }

    /**
     * Inserta los datos del bean en la tabla de informacion basica del egresado
     * 
     * @param Cursor
     *            cursor
     * @param ActualizarNuevoBasica
     *            basica
     * @param int
     *            tipo
     * @return boolean
     */
    public boolean actualizar(Cafeteria c) {
        int posicion = 1;
        Connection cn = null;
        PreparedStatement pst = null, pst2 = null, pst3 = null;
        try {
            cn = cursor.getConnection();
            cn.setAutoCommit(false);
            pst = cn.prepareStatement(rb.getString("CafeteriaActualizar"));
            pst.clearParameters();
            if (c.getResdescripcion().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, c.getResdescripcion().trim());
            if (c.getReshorario().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, c.getReshorario().trim());
            if (c.getRescosto().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setLong(posicion++, Long.parseLong(c.getRescosto().trim()));

            if (c.getRescodjerar().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setLong(posicion++, Long.parseLong(c.getRescodjerar()
                        .trim()));
            if (c.getRestiposerv().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(c.getRestiposerv()
                        .trim()));
            pst.executeUpdate();
            cn.commit();
            cn.setAutoCommit(true);
        } catch (InternalErrorException in) {
            setMensaje("NO se puede estabecer conexinn con la base de datos: ");
            return false;
        } catch (SQLException sqle) {
            try {
                cn.rollback();
            } catch (SQLException s) {
            }
            setMensaje("Error intentando actualizar cafeteria. Posible problema: ");
            switch (sqle.getErrorCode()) {
            default:
                setMensaje(sqle.getMessage().replace('\'', '`').replace('"',
                        'n'));
            }
            return false;
        } finally {
            try {
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            } catch (InternalErrorException inte) {
            }
        }
        return true;
    }

    /**
     * Funcinn: Insertar Institucinn <br>
     * 
     * @param Institucion
     *            i
     * @return boolean
     */
    public boolean insertar(Institucion i) {
        int posicion = 1; //posicion inicial de llenado del preparedstatement
        Connection cn = null;
        PreparedStatement pst = null, pst2 = null, pst3 = null;
        try {
            cn = cursor.getConnection();
            cn.setAutoCommit(false);
            pst = cn.prepareStatement(rb.getString("InstitucionInsertar"));
            pst.clearParameters();
            if (i.getInscoddane().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setLong(posicion++, Long
                        .parseLong(i.getInscoddane().trim()));
            if (i.getInscoddepto().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(i.getInscoddepto()
                        .trim()));
            if (i.getInscodmun().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer
                        .parseInt(i.getInscodmun().trim()));
            if (i.getInscodlocal().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(i.getInscodlocal()
                        .trim()));
            if (i.getInsnombre().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, i.getInsnombre().trim());
            if (i.getInsestado().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, i.getInsestado().trim());
            pst.executeUpdate();

            String[] resul = cursor
                    .getFila("select inscodigo from institucion where inscoddane="
                            + i.getInscoddane());
            posicion = 1;
            cn = cursor.getConnection();
            cn.setAutoCommit(false);
            pst = cn.prepareStatement(rb.getString("JerarquiaInsertar"));
            pst.clearParameters();
            pst.setInt(posicion++, 1);//TIPO 1
            pst.setInt(posicion++, 4);//NIVEL=4 INSTITUCIONES
            if (i.getInscoddepto().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(i.getInscoddepto()
                        .trim()));
            if (i.getInscodmun().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer
                        .parseInt(i.getInscodmun().trim()));
            if (i.getInscodlocal().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setLong(posicion++, Long.parseLong(i.getInscodlocal()
                        .trim()));
            if (resul[0].equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setLong(posicion++, Long.parseLong(resul[0].trim()));
            pst.setNull(posicion++, java.sql.Types.VARCHAR);
            pst.setNull(posicion++, java.sql.Types.VARCHAR);
            pst.setNull(posicion++, java.sql.Types.VARCHAR);
            pst.setNull(posicion++, java.sql.Types.VARCHAR);
            pst.setNull(posicion++, java.sql.Types.VARCHAR);
            pst.setNull(posicion++, java.sql.Types.VARCHAR);
            pst.executeUpdate();
            cn.commit();
            cn.setAutoCommit(true);
        } catch (InternalErrorException in) {
            setMensaje("NO se puede estabecer conexinn con la base de datos: ");
            return false;
        } catch (SQLException sqle) {
            try {
                cn.rollback();
            } catch (SQLException s) {
            }
            setMensaje("Error intentando ingresar información de institucion. Posible problema: ");
            switch (sqle.getErrorCode()) {
            default:
                setMensaje(sqle.getMessage().replace('\'', '`').replace('"',
                        'n'));
            }
            return false;
        } finally {
            try {
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            } catch (InternalErrorException inte) {
            }
        }
        return true;
    }

    /**
     * Inserta los datos del bean en la tabla de informacion basica del egresado
     * 
     * @param Cursor
     *            cursor
     * @param ActualizarNuevoBasica
     *            basica
     * @param int
     *            tipo
     * @return boolean
     */

    public boolean insertar(Jornada j) {
        int posicion = 1; //posicion inicial de llenado del preparedstatement
        Connection cn = null;
        PreparedStatement pst = null, pst2 = null, pst3 = null;
        try {
            cn = cursor.getConnection();
            cn.setAutoCommit(false);
            pst = cn.prepareStatement(rb.getString("JornadaInsertar"));
            String a[] = j.getJorcodigo();
            if (a != null) {
                for (int i = 0; i < a.length; i++) {
                    posicion = 1;
                    pst.clearParameters();
                    if (j.getJorcodins().equals(""))
                        pst.setNull(posicion++, java.sql.Types.VARCHAR);
                    else
                        pst.setLong(posicion++, Long.parseLong(j.getJorcodins()
                                .trim()));
                    if (a[i] == null || a[i].equals(""))
                        pst.setNull(posicion++, java.sql.Types.VARCHAR);
                    else
                        pst.setInt(posicion++, Integer.parseInt(a[i].trim()));
                    pst.executeUpdate();
                }
            }
            cn.commit();
            cn.setAutoCommit(true);
        } catch (InternalErrorException in) {
            setMensaje("NO se puede estabecer conexinn con la base de datos: ");
            return false;
        } catch (SQLException sqle) {
            try {
                cn.rollback();
            } catch (SQLException s) {
            }
            setMensaje("Error intentando ingresar jornadas. Posible problema: ");
            switch (sqle.getErrorCode()) {
            default:
                setMensaje(sqle.getMessage().replace('\'', '`').replace('"',
                        'n'));
            }
            return false;
        } finally {
            try {
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            } catch (InternalErrorException inte) {
            }
        }
        return true;
    }

    /**
     * Inserta los datos del bean en la tabla de informacion basica del egresado
     * 
     * @param Cursor
     *            cursor
     * @param ActualizarNuevoBasica
     *            basica
     * @param int
     *            tipo
     * @return boolean
     */
    public boolean insertar(Nivel nivel) {
        int posicion = 1; //posicion inicial de llenado del preparedstatement
        Connection cn = null;
        PreparedStatement pst = null, pst2 = null, pst3 = null;
        try {
            cn = cursor.getConnection();
            cn.setAutoCommit(false);
            pst = cn.prepareStatement(rb.getString("NivelInsertar"));
            String a[] = nivel.getNivcodigo();
            String b[] = nivel.getNivnombre();
            String c[] = nivel.getNivcodmetod() != null ? nivel
                    .getNivcodmetod() : new String[a.length];
            String d[] = nivel.getNivorden();
            if (a != null) {
                for (int i = 0; i < a.length; i++) {
                    posicion = 1;
                    pst.clearParameters();
                    if (nivel.getNivcodinst().equals(""))
                        pst.setNull(posicion++, java.sql.Types.VARCHAR);
                    else
                        pst.setLong(posicion++, Long.parseLong(nivel
                                .getNivcodinst().trim()));
                    if (a[i] == null || a[i].equals(""))
                        pst.setNull(posicion++, java.sql.Types.VARCHAR);
                    else
                        pst.setInt(posicion++, Integer.parseInt(a[i].trim()));
                    if (b[i] == null || b[i].equals(""))
                        pst.setNull(posicion++, java.sql.Types.VARCHAR);
                    else
                        pst.setString(posicion++, b[i].trim());
                    if (c[i] == null || c[i].equals(""))
                        pst.setNull(posicion++, java.sql.Types.VARCHAR);
                    else
                        pst.setInt(posicion++, Integer.parseInt(c[i].trim()));
                    if (d[i] == null || d[i].equals(""))
                        pst.setNull(posicion++, java.sql.Types.VARCHAR);
                    else
                        pst.setInt(posicion++, Integer.parseInt(d[i].trim()));
                    pst.executeUpdate();
                }
            }
            cn.commit();
            cn.setAutoCommit(true);
        } catch (InternalErrorException in) {
            setMensaje("NO se puede estabecer conexinn con la base de datos: ");
            return false;
        } catch (SQLException sqle) {
            try {
                cn.rollback();
            } catch (SQLException s) {
            }
            setMensaje("Error intentando ingresar niveles. Posible problema: ");
            switch (sqle.getErrorCode()) {
            default:
                setMensaje(sqle.getMessage().replace('\'', '`').replace('"',
                        'n'));
            }
            return false;
        } finally {
            try {
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            } catch (InternalErrorException inte) {
            }
        }
        return true;
    }

    /**
     * Inserta los datos del bean en la tabla de informacion basica del egresado
     * 
     * @param Cursor
     *            cursor
     * @param ActualizarNuevoBasica
     *            basica
     * @param int
     *            tipo
     * @return boolean
     */

    public boolean insertar(Sede s, String[] jer) {
        int posicion = 1; //posicion inicial de llenado del preparedstatement
        int posicion2 = 1; //posicion inicial de llenado del preparedstatement
        Connection cn = null;
        PreparedStatement pst = null, pst2 = null, pst3 = null;
        try {
            cn = cursor.getConnection();
            cn.setAutoCommit(false);
            pst = cn.prepareStatement(rb.getString("SedeInsertar"));
            pst.clearParameters();
            if (s.getSedcodins().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setLong(posicion++, Long.parseLong(s.getSedcodins().trim()));
            if (s.getSedcodigo().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer
                        .parseInt(s.getSedcodigo().trim()));
            if (s.getSedcoddaneanterior().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setLong(posicion++, Long.parseLong(s
                        .getSedcoddaneanterior().trim()));
            if (s.getSedtipo().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(s.getSedtipo().trim()));
            if (s.getSednombre().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, s.getSednombre().trim());
            if (s.getSedzona().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(s.getSedzona().trim()));
            if (s.getSedvereda().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, s.getSedvereda().trim());
            if (s.getSedbarrio().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, s.getSedbarrio().trim());
            if (s.getSedresguardo().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, s.getSedresguardo().trim());
            if (s.getSeddireccion().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, s.getSeddireccion().trim());
            if (s.getSedtelefono().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, s.getSedtelefono().trim());
            if (s.getSedfax().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, s.getSedfax().trim());
            if (s.getSedcorreo().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, s.getSedcorreo().trim());
            if (s.getSedagua().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(s.getSedagua().trim()));
            if (s.getSedluz().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(s.getSedluz().trim()));
            if (s.getSedtel().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(s.getSedtel().trim()));
            if (s.getSedalcantarillado().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(s
                        .getSedalcantarillado().trim()));
            if (s.getSedgas().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(s.getSedgas().trim()));
            if (s.getSedinternet().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(s.getSedinternet()
                        .trim()));
            if (s.getSedinternettipo().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(s.getSedinternettipo()
                        .trim()));
            if (s.getSedinternetprov().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, s.getSedinternetprov().trim());
            pst.executeUpdate();
            //JERARQUIA PARA SEDE
            posicion = 1;
            pst.close();
            pst = cn.prepareStatement(rb.getString("JerarquiaInsertar"));
            pst.clearParameters();
            pst.setInt(posicion++, 1);//TIPO 1
            pst.setInt(posicion++, 5);//NIVEL=5 SEDES
            if (jer[0].equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(jer[0].trim()));
            if (jer[1].equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(jer[1].trim()));
            if (jer[2].equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setLong(posicion++, Long.parseLong(jer[2].trim()));
            if (s.getSedcodins().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst
                        .setLong(posicion++, Long.parseLong(s.getSedcodins()
                                .trim()));
            if (s.getSedcodigo().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst
                        .setLong(posicion++, Long.parseLong(s.getSedcodigo()
                                .trim()));
            pst.setNull(posicion++, java.sql.Types.VARCHAR);
            pst.setNull(posicion++, java.sql.Types.VARCHAR);
            pst.setNull(posicion++, java.sql.Types.VARCHAR);
            pst.setNull(posicion++, java.sql.Types.VARCHAR);
            pst.setNull(posicion++, java.sql.Types.VARCHAR);
            pst.executeUpdate();

            //INSERTAR SEDES POR JORNADA
            pst.close();
            pst = cn.prepareStatement(rb.getString("SedeJornadaInsertar"));
            pst2 = cn.prepareStatement(rb.getString("JerarquiaInsertar"));
            String[] a = s.getSedjorcodjor();
            if (a != null) {
                for (int i = 0; i < a.length; i++) {
                    posicion = 1;
                    pst.clearParameters();
                    if (s.getSedcodins().equals(""))
                        pst.setNull(posicion++, java.sql.Types.VARCHAR);
                    else
                        pst.setLong(posicion++, Long.parseLong(s.getSedcodins()
                                .trim()));
                    if (s.getSedcodigo().equals(""))
                        pst.setNull(posicion++, java.sql.Types.VARCHAR);
                    else
                        pst.setInt(posicion++, Integer.parseInt(s
                                .getSedcodigo().trim()));
                    if (a[i] == null || a[i].equals(""))
                        pst.setNull(posicion++, java.sql.Types.VARCHAR);
                    else
                        pst.setInt(posicion++, Integer.parseInt(a[i].trim()));
                    pst.executeUpdate();

                    //JERARQUIA DE JORNADAS
                    posicion2 = 1;
                    pst2.clearParameters();
                    pst2.setInt(posicion2++, 1);//TIPO 1
                    pst2.setInt(posicion2++, 6);//NIVEL=6 JORNADAS
                    if (jer[0].equals(""))
                        pst2.setNull(posicion2++, java.sql.Types.VARCHAR);
                    else
                        pst2.setInt(posicion2++, Integer
                                .parseInt(jer[0].trim()));
                    if (jer[1].equals(""))
                        pst2.setNull(posicion2++, java.sql.Types.VARCHAR);
                    else
                        pst2.setInt(posicion2++, Integer
                                .parseInt(jer[1].trim()));
                    if (jer[2].equals(""))
                        pst2.setNull(posicion2++, java.sql.Types.VARCHAR);
                    else
                        pst2
                                .setLong(posicion2++, Long.parseLong(jer[2]
                                        .trim()));
                    if (s.getSedcodins().equals(""))
                        pst2.setNull(posicion2++, java.sql.Types.VARCHAR);
                    else
                        pst2.setLong(posicion2++, Long.parseLong(s
                                .getSedcodins().trim()));
                    if (s.getSedcodigo().equals(""))
                        pst2.setNull(posicion2++, java.sql.Types.VARCHAR);
                    else
                        pst2.setLong(posicion2++, Long.parseLong(s
                                .getSedcodigo().trim()));
                    if (a[i] == null || a[i].equals(""))
                        pst2.setNull(posicion2++, java.sql.Types.VARCHAR);
                    else
                        pst2.setInt(posicion2++, Integer.parseInt(a[i].trim()));
                    pst2.setNull(posicion2++, java.sql.Types.VARCHAR);
                    pst2.setNull(posicion2++, java.sql.Types.VARCHAR);
                    pst2.setNull(posicion2++, java.sql.Types.VARCHAR);
                    pst2.setNull(posicion2++, java.sql.Types.VARCHAR);
                    pst2.executeUpdate();
                }
            }
            //INSERTAR JORNADAS POR NIVEL
            pst.close();
            pst = cn.prepareStatement(rb.getString("SedeJornadaNivelInsertar"));
            a = s.getSedjornivcodnivel();
            String n1 = "", n2 = "";
            int aux;
            if (a != null) {
                for (int i = 0; i < a.length; i++) {
                    posicion = 1;
                    pst.clearParameters();
                    aux = a[i].lastIndexOf("|");
                    n1 = a[i].substring(0, aux);
                    n2 = a[i].substring(aux + 1, a[i].length());
                    if (s.getSedcodins().equals(""))
                        pst.setNull(posicion++, java.sql.Types.VARCHAR);
                    else
                        pst.setLong(posicion++, Long.parseLong(s.getSedcodins()
                                .trim()));
                    if (s.getSedcodigo().equals(""))
                        pst.setNull(posicion++, java.sql.Types.VARCHAR);
                    else
                        pst.setInt(posicion++, Integer.parseInt(s
                                .getSedcodigo().trim()));
                    if (n1 == null || n1.equals(""))
                        pst.setNull(posicion++, java.sql.Types.VARCHAR);
                    else
                        pst.setInt(posicion++, Integer.parseInt(n1.trim()));
                    if (n2 == null || n2.equals(""))
                        pst.setNull(posicion++, java.sql.Types.VARCHAR);
                    else
                        pst.setInt(posicion++, Integer.parseInt(n2.trim()));
                    pst.executeUpdate();
                }
            }
            cn.commit();
            cn.setAutoCommit(true);
        } catch (InternalErrorException in) {
            setMensaje("NO se puede estabecer conexinn con la base de datos: ");
            return false;
        } catch (SQLException sqle) {
            try {
                cn.rollback();
            } catch (SQLException se) {
            }
            setMensaje("Error intentando ingresar sede. Posible problema: ");
            String mensaje;
            switch (sqle.getErrorCode()) {
            default:
                setMensaje(sqle.getMessage().replace('\'', '`').replace('"',
                        'n'));
            }
            return false;
        } finally {
            try {
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeStatement(pst2);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            } catch (InternalErrorException inte) {
            }
        }
        return true;
    }

    /**
     * Inserta los datos del bean en la tabla de informacion basica del egresado
     * 
     * @param Cursor
     *            cursor
     * @param ActualizarNuevoBasica
     *            basica
     * @param int
     *            tipo
     * @return boolean
     */

    public synchronized  boolean insertar(Espacio e) {
        int posicion = 1; //posicion inicial de llenado del preparedstatement
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs=null;        
        try{
            long inst=Long.parseLong(e.getEspcodins().trim());
            long sed=Long.parseLong(e.getEspcodsede().trim());
            long cod=0;
            cn = cursor.getConnection();
            cn.setAutoCommit(false);
            pst = cn.prepareStatement(rb.getString("ConsecutivoEspacio"));
            pst.clearParameters();
            pst.setLong(posicion++, inst);
            pst.setLong(posicion++, sed);
            rs=pst.executeQuery();
            if(rs.next()){
              cod=rs.getLong(1);
            }
            rs.close();pst.close();posicion = 1;
            pst = cn.prepareStatement(rb.getString("EspacioInsertar"));
            pst.clearParameters();
            pst.setLong(posicion++, inst);
            pst.setLong(posicion++, sed);
            pst.setLong(posicion++, cod);
            pst.setInt(posicion++, Integer.parseInt(e.getEsptipo().trim()));
            pst.setString(posicion++, e.getEspnombre().trim());
            pst.setLong(posicion++, Long.parseLong(e.getEspcapacidad().trim()));
            if (e.getEsparea().equals("")) pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setLong(posicion++, Long.parseLong(e.getEsparea().trim()));
            if (e.getEsptipoocupante().equals("")) pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setInt(posicion++, Integer.parseInt(e.getEsptipoocupante().trim()));
            if (e.getEspestado().equals("")) pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setInt(posicion++, Integer.parseInt(e.getEspestado().trim()));
            if (e.getEspfuncion().equals("")) pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setString(posicion++, e.getEspfuncion().trim());
            if (e.getEspdescripcion().equals("")) pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else pst.setString(posicion++, e.getEspdescripcion().trim());
            pst.executeUpdate();
            pst.close();
            String[] a = e.getSedjorespcodjor();
            if (a != null) {
                pst = cn.prepareStatement(rb.getString("SedeJornadaEspacioInsertar"));
                for (int i = 0; i < a.length; i++) {
                    posicion = 1;
                    pst.clearParameters();
                    pst.setLong(posicion++, inst);
                    pst.setLong(posicion++, sed);
                    pst.setLong(posicion++, cod);
                    if (a[i] == null || a[i].equals("")) pst.setNull(posicion++, java.sql.Types.VARCHAR);
                    else pst.setInt(posicion++, Integer.parseInt(a[i].trim()));
                    pst.executeUpdate();
                }
            }
            cn.commit();
            cn.setAutoCommit(true);
        } catch (InternalErrorException in) {setMensaje("NO se puede estabecer conexinn con la base de datos: ");return false;}
        catch (SQLException sqle) {
            try{cn.rollback();} catch (SQLException s){}
            setMensaje("Error intentando ingresar espacio fisico. Posible problema: ");
            switch (sqle.getErrorCode()) {
            default:
                setMensaje(sqle.getMessage().replace('\'', '`').replace('"','n'));
            }
            return false;
        } finally {
            try {
                OperacionesGenerales.closeResultSet(rs);
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            } catch (InternalErrorException inte) {}
        }
        return true;
    }

    /**
     * Inserta los datos del bean en la tabla de informacion basica del egresado
     * 
     * @param Cursor
     *            cursor
     * @param ActualizarNuevoBasica
     *            basica
     * @param int
     *            tipo
     * @return boolean
     */
    public boolean insertar(Transporte t) {
        int posicion = 1;
        Connection cn = null;
        PreparedStatement pst = null, pst2 = null, pst3 = null;
        try {
            cn = cursor.getConnection();
            cn.setAutoCommit(false);
            pst = cn.prepareStatement(rb.getString("TransporteInsertar"));
            pst.clearParameters();
            if (t.getTracodjerar().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setLong(posicion++, Long.parseLong(t.getTracodjerar()
                        .trim()));
            if (t.getTracodruta().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(t.getTracodruta()
                        .trim()));
            if (t.getTranombre().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, t.getTranombre().trim());
            if (t.getTraconductor().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, t.getTraconductor().trim());
            if (t.getTracelular().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, t.getTracelular().trim());
            if (t.getTracupos().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst
                        .setInt(posicion++, Integer.parseInt(t.getTracupos()
                                .trim()));
            if (t.getTraplaca().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, t.getTraplaca().trim());
            if (t.getTraciudad().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, t.getTraciudad().trim());
            if (t.getTradescripcion().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, t.getTradescripcion().trim());
            if (t.getTracostocompleto().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setLong(posicion++, Long.parseLong(t.getTracostocompleto()
                        .trim()));
            if (t.getTracostomedia().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setLong(posicion++, Long.parseLong(t.getTracostomedia()
                        .trim()));
            pst.executeUpdate();
            cn.commit();
            cn.setAutoCommit(true);
        } catch (InternalErrorException in) {
            setMensaje("NO se puede estabecer conexinn con la base de datos: ");
            return false;
        } catch (SQLException sqle) {
            try {
                cn.rollback();
            } catch (SQLException s) {
            }
            setMensaje("Error intentando ingresar transporte. Posible problema: ");
            switch (sqle.getErrorCode()) {
            default:
                setMensaje(sqle.getMessage().replace('\'', '`').replace('"',
                        'n'));
            }
            return false;
        } finally {
            try {
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            } catch (InternalErrorException inte) {
            }
        }
        return true;
    }

    /**
     * Inserta los datos del bean en la tabla de informacion basica del egresado
     * 
     * @param Cursor
     *            cursor
     * @param ActualizarNuevoBasica
     *            basica
     * @param int
     *            tipo
     * @return boolean
     */
    public boolean insertar(Cafeteria c) {
        int posicion = 1;
        Connection cn = null;
        PreparedStatement pst = null, pst2 = null, pst3 = null;
        try {
            cn = cursor.getConnection();
            cn.setAutoCommit(false);
            pst = cn.prepareStatement(rb.getString("CafeteriaInsertar"));
            pst.clearParameters();
            if (c.getRescodjerar().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setLong(posicion++, Long.parseLong(c.getRescodjerar()
                        .trim()));
            if (c.getRestiposerv().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setInt(posicion++, Integer.parseInt(c.getRestiposerv()
                        .trim()));
            if (c.getResdescripcion().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, c.getResdescripcion().trim());
            if (c.getReshorario().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setString(posicion++, c.getReshorario().trim());
            if (c.getRescosto().equals(""))
                pst.setNull(posicion++, java.sql.Types.VARCHAR);
            else
                pst.setLong(posicion++, Long.parseLong(c.getRescosto().trim()));
            pst.executeUpdate();
            cn.commit();
            cn.setAutoCommit(true);
        } catch (InternalErrorException in) {
            setMensaje("NO se puede estabecer conexinn con la base de datos: ");
            return false;
        } catch (SQLException sqle) {
            try {
                cn.rollback();
            } catch (SQLException s) {
            }
            setMensaje("Error intentando ingresar cafeteria. Posible problema: ");
            switch (sqle.getErrorCode()) {
            default:
                setMensaje(sqle.getMessage().replace('\'', '`').replace('"',
                        'n'));
            }
            return false;
        } finally {
            try {
                OperacionesGenerales.closeStatement(pst);
                OperacionesGenerales.closeConnection(cn);
                cursor.cerrar();
            } catch (InternalErrorException inte) {
            }
        }
        return true;
    }
}