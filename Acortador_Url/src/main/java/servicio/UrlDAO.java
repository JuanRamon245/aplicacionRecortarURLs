package servicio;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import control.HibernateUtil;
import recursos.Recursos;

import clases.Url;

public class UrlDAO {

    private static final Logger logger = LogManager.getLogger(UrlDAO.class);

    /*
     * Mét0do para insertar la URL sin verificar si ya existe en la BBDD
     *
     * @Param url Objeto url del usuario
     */
    public void insertarUrl(Url url) {
    	Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(url);
            transaction.commit();
            logger.info("URL insertada exitosamente: {}", url);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            logger.error("Error al insertar URL", e);
        }
    }

    /*
     * Mét0do para insertar la URL verificando si ya existe en la BBDD
     *
     * @Param url Objeto url del usuario
     */
    public void insertarUrlVerificandoExistencia (Url url) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            while (verificaciónUrlCorta(url.getUrlCorta())) {
                url.setUrlCorta(Recursos.generarIdAleatorio(7));
            }

            session.save(url);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /*
     * Mét0do para verificar si la URL acortada ya existe en la base de datos
     *
     * @Param urlCorta String con la URL sin acortar
     */
    public boolean verificaciónUrlCorta(String urlCorta) {
    	Session session = HibernateUtil.getSessionFactory().openSession();
        boolean existe = false;
        try{
            String sentenciaSQL = "SELECT COUNT(*) FROM Url WHERE urlCorta = :urlCorta";
            Query<Long> query = session.createQuery(sentenciaSQL, Long.class);
            query.setParameter("urlCorta", urlCorta);
            existe = query.uniqueResult() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return existe;
    }

    /*
     * Mét0do para obtener el objeto Url por medio de una busqueda en la base de datos con la URL larga del usuario
     *
     * @Param urlLarga String con la URL sin acortar
     */
    public Url obtenerUrl(String urlLarga) {
    	Session session = HibernateUtil.getSessionFactory().openSession();
        Url url = null;
        try {
            url = session.get(Url.class, urlLarga);
            logger.info("URL obtenida exitosamente: {}", url);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error al obtener URL", e);
        }
        return url;
    }

    /*
     * Mét0do para eliminar el objeto Url por medio de una busqueda con la urlLarga
     *
     * @Param urlLarga String con la URL sin acortar
     */
    public void eliminarUrl(String urlLarga) {
    	Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Url url = session.get(Url.class, urlLarga);
            if (url != null) {
                session.delete(url);
            }
            transaction.commit();
            logger.info("URL borrada exitosamente: {}", url);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                logger.error("Error al eliminar URL", e);
            }
            e.printStackTrace();
        }
    }

    /*
     * Mét0do para obtener todos los objetos Url de la base de datos
     *
     * @return List<Url> lista con todos los objetos Url de la base de datos
     */
    public List<Url> obtenerTodasUrls() {
    	Session session = HibernateUtil.getSessionFactory().openSession();
        List<Url> contactos = new ArrayList<>();
        try {
            Transaction transaction = session.beginTransaction();
            Query<Url> query = session.createQuery("FROM Url", Url.class);
            contactos = query.list();
            transaction.commit();
            logger.info("Lista de URLs obtenida exitosamente: {}");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error al obtener la lista de las URLs", e);
        }
        return contactos;
    }

    /*
     * Mét0do para verificar si la URL sin acortar ya existe en la base de datos
     *
     * @Param urlLarga String con la URL sin acortar
     */
    public boolean existeUrlLarga(String urlLarga) {
        boolean existeUrl = false;
        try {
            List<Url> listaUrl = obtenerTodasUrls();
            for (Url url : listaUrl) {
                if (url.getUrlLarga().equals(urlLarga)) {
                    existeUrl = true;
                    logger.info("URL existe en la base de datos: {}", url);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("La URLLarga no se ha encontrado en la base de datos: {}", urlLarga);
        }
        return existeUrl;
    }

    /*
     * Mét0do para devolver la url acortada asociada a la url sin acortar
     *
     * @Param urlLarga String con la URL sin acortar
     */
    public String devuelveUrlCorta(String urlLarga) {
        String urlCorta = "";
        try {
            List<Url> listaUrl = obtenerTodasUrls();
            for (Url url : listaUrl) {
                if (url.getUrlLarga().equals(urlLarga)) {
                    urlCorta = url.getUrlCorta();
                    logger.info("URLCorta devuelta: {}", url);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("La URLCorta no se ha devuelto");
        }
        return urlCorta;
    }

    /*
     * Mét0do para verificar si la URL acortada ya existe en la base de datos
     *
     * @Param urlCorta String con la URL sin acortar
     */
    public boolean existeUrlCorta(String urlCorta) {
        boolean existeUrl = false;
        try {
            List<Url> listaUrl = obtenerTodasUrls();
            for (Url url : listaUrl) {
                if (url.getUrlCorta().equals(urlCorta)) {
                    existeUrl = true;
                    logger.info("URLCorta se ha encontrado en la base de datos: {}", url);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("URLCorta no se ha encontrado en la base de datos: {}", urlCorta);
        }
        return existeUrl;
    }

    /*
     * Mét0do para devolver la url sin acortar asociada a la url acortada
     *
     * @Param urlCorta String con la URL sin acortada
     */
    public String devuelveUrLarga(String urlCorta) {
        String urlLarga = "";
        try {
            List<Url> listaUrl = obtenerTodasUrls();
            for (Url url : listaUrl) {
                if (url.getUrlCorta().equals(urlCorta)) {
                    urlLarga = url.getUrlLarga();
                    logger.info("URLLarga se ha encontrado en la base de datos: {}", urlLarga);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("URLLarga no se ha encontrado en la base de datos");
        }
        return urlLarga;
    }

    /*
     * Mét0do para actualizar el numero de intentos de la url larga asociada
     *
     * @Param urlCorta String con la URL sin acortada
     */
    public void actualizarNumeroIntentos(String urlLarga) {
    	Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Url urlObtenida = session.get(Url.class, urlLarga);
            if (urlObtenida != null) {
                urlObtenida.setVecesUsada(urlObtenida.getVecesUsada() + 1);
            }
            transaction.commit();
            session.close();
        }  catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();

            }
            e.printStackTrace();
            logger.warn("No se ha podido el número de intentos de la URL");
        }
    }

    /*
     * Mét0do para actualizar los navegadores que se han usado para abrir la url acortada y redirecionarse a la url larga
     *
     * @Param urlCorta String con la URL sin acortada
     * @Param userAgent String con el codigo del navegador usado
     */
    public void actualizarNavegadores(String urlLarga, String userAgent) {
        String navegador = obtenerNavegador(userAgent);

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            Url urlObtenida = session.get(Url.class, urlLarga);

            if (urlObtenida != null) {
                String navegadoresActuales = urlObtenida.getNavegadores();
                Set<String> navegadoresSet = new TreeSet<>();

                if (navegadoresActuales != null && !navegadoresActuales.isEmpty()) {
                    navegadoresSet.addAll(Arrays.asList(navegadoresActuales.split("/")));
                }

                navegadoresSet.add(navegador);

                String navegadoresActualizados = String.join("/", navegadoresSet);
                urlObtenida.setNavegadores(navegadoresActualizados);
            }
            transaction.commit();
            session.close();
        }  catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();

            }
            e.printStackTrace();
            logger.warn("No se ha podido actualizar los navegadores de la url corta");
        }
    }

    private String obtenerNavegador(String userAgent) {
        if (userAgent.contains("OPR/") || userAgent.contains("Opera")) {
            return "Opera";
        } else if (userAgent.contains("Edg/")) {
            return "Microsoft edge";
        } else if (userAgent.contains("Brave/") || userAgent.toLowerCase().contains("brave")) {
            return "Brave";
        } else if (userAgent.contains("Chrome/") && !userAgent.contains("OPR/") && !userAgent.contains("Edg/") && !userAgent.toLowerCase().contains("brave")) {
            return "Chrome";
        } else if (userAgent.contains("Firefox/")) {
            return "Firefox";
        } else if (userAgent.contains("Safari/") && !userAgent.contains("Chrome/")) {
            return "Safari";
        } else {
            return "Desconocido";
        }
    }
    
}
