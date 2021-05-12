package com.kevingomez.FYCBackEnd.models.DAO.Services.Impl;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

import java.io.FileReader;

import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;
import com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces.IUsuarioDAO;
import com.kevingomez.FYCBackEnd.models.entity.Coches.Marca;
import com.kevingomez.FYCBackEnd.models.entity.Usuarios.Usuario;
import org.aspectj.weaver.IUnwovenClassFile;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces.IFicherosService;
import com.kevingomez.FYCBackEnd.models.DAO.dao.Interfaces.IMarcaDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class FicherosService implements IFicherosService {

    private static final Logger log = LoggerFactory.getLogger(FicherosService.class);

    @Autowired
    private IMarcaDAO marcaDAO;

    @Autowired
    private IUsuarioDAO usuarioDAO;

    @Value("${accessTokenDropbox}")
    private String ACCESS_TOKEN;

    @Value("${uploadPath.Image}")
    private String UPLOAD_DIR;

    @Value("${propietario.Image}")
    private String PROPIETARIO_NAME;

    private final String LINKS_MARCAS = "sharedlinksMarcas.json";
    private final String LINKS_MODELOS = "sharedlinksModelos.json";
    private final String LINKS_USUARIOS = "sharedlinksUsuarios.json";
    private final String PATH_USUARIOS = "/files/usuarios/";
    private final String PATH_MARCAS = "/files/marcas/";
    private final String PATH_LINKS = "/files/links/";
    private static String tipo;
    private DbxClientV2 client;

    /**
     * Metodo para configurar el cliente dropbox
     *
     * @return
     */
    private DbxClientV2 createClient() {
        if (client != null) {
            return client;
        } else {
            DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox").build();
            client = new DbxClientV2(config, ACCESS_TOKEN);
            return client;
        }
    }

//    @Override
//    public void deleteUser(int id){
//        Object obj = retrieveLinkFile(LINKS_USUARIOS);
//        JSONObject jsonObject = (JSONObject) obj;
//        JSONObject finalJsonObject = (JSONObject) jsonObject.get("linkusuarios");
//        if (finalJsonObject.getOrDefault(String.valueOf(id), null) != null) {
//            // Si el usuario ya tenia foto de perfil
//            finalJsonObject.remove(Integer.toString(id));
//            saveFileLocaleAndRemote(jsonObject, LINKS_USUARIOS);
//        }
//
//    }

    /**
     * Metodo para subir un archivo a dropbox
     */
    public String uploadFile(String area, MultipartFile file, String filename) {
        try {
            HashMap<String, String> paths = getLocalAndRemotePaths(area);
            String pathRemote = paths.get("remote");
            DbxClientV2 client = createClient();
            if (filename.equals("")) {
                filename = file.getOriginalFilename();
            }
            //Comprobarmos si ya se encuentra el archivo repetido y borramos el antiguo
            ListFolderResult result = client.files().listFolder(pathRemote);
            boolean breakWhile = false;
            while (true) {
                for (Metadata metadata : result.getEntries()) {
                    //Obtenemos el nombre sin extension del fichero y los comparamos con el nuevo archivo
                    String remoteNameWithDot = metadata.getPathDisplay().split("/")[3];
                    String remoteNameWithoutDot = remoteNameWithDot.split("\\.")[0];
                    assert filename != null;
                    if (remoteNameWithoutDot.equals(filename.split("\\.")[0])) {
                        client.files().deleteV2(pathRemote + remoteNameWithDot);
                        breakWhile = true;
                        break;
                    }
                }
                if (!result.getHasMore() || breakWhile) {
                    break;
                }

                result = client.files().listFolderContinue(result.getCursor());
            }
            client.files().uploadBuilder(pathRemote + filename).uploadAndFinish(file.getInputStream());
            log.info("Fichero subido correctamente.");
            return "Fichero subido correctamente.";

        } catch (IOException e) {
            log.error(e.getMessage().concat(": ").concat(e.getCause().getMessage()));
            return e.getMessage().concat(": ").concat(e.getCause().getMessage());
        } catch (DbxException e) {
            log.error("Error al subir el fichero: " + e.getMessage());
            return "Error al subir el fichero.";
        }
    }

//    public Path getFile(String area, String filename) throws IOException, DbxException {
////        try {
//            return downloadFile(area, filename);
//
////            DbxClientV2 client = createClient();
////
////            File dir = new File(localPath);
////            if (!dir.exists()) dir.mkdirs();
////            localPath += "/" + filename;
////            OutputStream outputStream = null;
////            try {
////                outputStream = new FileOutputStream(localPath);
////            } catch (FileNotFoundException e) {
////                log.error(e.getCause().getMessage());
////            }
////            try {
////                FileMetadata metadata = client.files()
////                        .downloadBuilder(pathRemote + filename)
////                        .download(outputStream);
////            } catch (DbxException | IOException e) {
////                e.printStackTrace();
////            }
////            log.info("Fichero " + filename + " descargado");
////            } catch (IOException e) {
////            log.error(e.getMessage().concat(": ").concat(e.getCause().getMessage()));
//////            return e.getMessage().concat(": ").concat(e.getCause().getMessage());
////        } catch (DbxException e) {
////            log.error("Error al conectarse al servidor de archivos");
//////            return "Error al conectarse al servidor de archivos";
////        }
//
//    }

    /**
     * Metodo para obtener los path local y remoto
     * @param area
     * @return
     */
    private HashMap<String, String> getLocalAndRemotePaths(String area){
        HashMap<String, String> paths = new HashMap<>();
        switch (area) {
            case "userImage":
                paths.put("remote",PATH_USUARIOS);
                paths.put("local",Paths.get("src/main/resources/static/users/").toAbsolutePath().toString());
                break;
            case "links":
                paths.put("remote",PATH_LINKS);
                paths.put("local",Paths.get("src/main/resources/static/linkfiles/").toAbsolutePath().toString());
                break;
            case "marcas":
                paths.put("remote",PATH_MARCAS);
                break;
        }
        return paths;
    }

    /**
     * Metodo para descargar un fichero
     *
     * @param area     Tipo de area al que pertenece el fichero
     * @param filename Nombre del fichero
     * @return Path del fichero descargado
     * @throws DbxException
     * @throws IOException
     */
    public Path downloadFile(String area, String filename) throws DbxException, IOException {

        HashMap<String, String> paths = getLocalAndRemotePaths(area);
        String pathRemote = paths.get("remote");
        String localPath = paths.get("local");
        DbxClientV2 client = createClient();
        File dir = new File(localPath);
        if (!dir.exists()) dir.mkdirs();
        localPath += "/" + filename;
        OutputStream outputStream = new FileOutputStream(localPath);
        FileMetadata metadata = client.files().downloadBuilder(pathRemote + filename).download(outputStream);
//        } catch (FileNotFoundException e) {
//            log.error(e.getCause().getMessage());
//        } catch (DbxException | IOException e) {
//            e.printStackTrace();
//        }
        log.info("Fichero " + filename + " descargado");
        outputStream.close();
        return Paths.get(localPath);

    }

    @Override
    public void setURLMarca(Marca marca) {
        int id = marca.getIdMarca();
        Object obj = retrieveLinkFile(LINKS_MARCAS);
        JSONObject jsonObject = (JSONObject) obj;
        JSONObject finalJsonObject = (JSONObject) jsonObject.get("linkmarcas");
        try {
            SharedLinkMetadata sharedLinkMetadata;
            sharedLinkMetadata = client.sharing().createSharedLinkWithSettings(PATH_MARCAS + marca.getMarcaCoche()+".png");

            if (finalJsonObject.getOrDefault(String.valueOf(id), null) != null) {
                // Si la marca ya tenia imagen
                ((JSONObject) finalJsonObject.get(Integer.toString(id))).replace("url", sharedLinkMetadata.getUrl().replace("https://www.dropbox.com/", "https://dl.dropboxusercontent.com/"));
            } else {
                // Si es la primera imagen de la marca
                String stringID = String.valueOf(id);
                JSONObject newObj = new JSONObject();
                newObj.put("id", stringID);
                newObj.put("url", sharedLinkMetadata.getUrl().replace("https://www.dropbox.com/", "https://dl.dropboxusercontent.com/"));
                finalJsonObject.put(stringID, newObj);
            }
            saveFileLocaleAndRemote(jsonObject, LINKS_MARCAS);

        } catch (DbxException e) {
            log.error("Error al actualizar la imagen de la marca en los ficheros " + LINKS_MARCAS);
        }

    }

    /**
     * Metodo para recuperar el fichero de links
     *
     * @param nombreFichero
     * @return
     */
    private Object retrieveLinkFile(String nombreFichero) {
        try {
            JSONParser parser = new JSONParser();
            // Comprobamos si existe el fichero y si no lo descargamos
            File sharelinks = new File(String.valueOf(Paths.get("src/main/resources/static/linkfiles/").resolve(nombreFichero).toAbsolutePath()));
            if (!sharelinks.exists()) {
                log.info("Se crea el fichero " + nombreFichero);
                downloadFile("links", nombreFichero);
            }
            return parser.parse(new FileReader(String.valueOf(Paths.get("src/main/resources/static/linkfiles/").resolve(nombreFichero).toAbsolutePath())));
        } catch (IOException | ParseException | DbxException e) {
            log.error(e.getMessage());
        }
        return null;
    }


    /**
     * Metodo para retornar todos los logos de las marcas
     *
     * @return Logos de las marcas
     */
    @Override
    @Transactional(readOnly = true) //Select solo de lectura
    public HashMap<Integer, String> getURLMarcaLogo(int idMarca, boolean varias) {

        //TODO para crear enlace compartido cuando se añade una nueva marca con dropbox
//            Marca marca = marcaDAO.findById(idMarca).orElse(null);
//            SharedLinkMetadata sharedLinkMetadata;
//            if (marca != null) {
//                sharedLinkMetadata = client.sharing().createSharedLinkWithSettings("/files/marcas/" + marca.getMarcaCoche() + ".png");
//                System.out.println(sharedLinkMetadata.getUrl());
//            } else {
//                sharedLinkMetadata = client.sharing().createSharedLinkWithSettings("/files/marcas/" + "defaultImage" + ".png");
//                System.out.println(sharedLinkMetadata.getUrl());
//            }


//                String localPath = "src/main/resources/static/images/marcas/Aston Martin.png";
//            OutputStream outputStream = new FileOutputStream(localPath);
//            FileMetadata metadata = client.files()
//                    .downloadBuilder("/files/marcas/"+marca.getMarcaCoche() + ".png")
//                    .download(outputStream);
//            log.info("Descargando imagen");

        log.info("Recuperando urls de las marcas");
        HashMap<Integer, String> url = new HashMap<>();

        Object obj = retrieveLinkFile(LINKS_MARCAS);
        JSONObject jsonObject = (JSONObject) obj;
        jsonObject = (JSONObject) jsonObject.get("linkmarcas");
        if (varias) {
            jsonObject.forEach((idMarcaFE, object) -> {
                url.put(Integer.valueOf(idMarcaFE.toString()), ((JSONObject) object).get("url").toString());
            });
//                System.out.println(blog);

//
//                // loop array
//                JSONArray tags = (JSONArray) jsonObject.get("Tags");
//                Iterator<String> iterator = tags.iterator();
//                while (iterator.hasNext()) {
//                    System.out.println(iterator.next());
//                }
//                marcaDAO.findAll().forEach(marca -> {
//                    try {
//                        url.put(marca.getIdMarca(),
//                                client.sharing().listSharedLinksBuilder()
//                                        .withPath("/files/marcas/" + marca.getMarcaCoche() + ".png")
//                                        .withDirectOnly(true)
//                                        .start()
//                                        .getLinks()
//                                        .get(0)
//                                        .getUrl()
//                                        .replace("https://www.dropbox.com/","https://dl.dropboxusercontent.com/"));
//                    } catch (DbxException e) {
//                        e.printStackTrace();
//                    }
//                });
        } else {
            if (jsonObject.getOrDefault(String.valueOf(idMarca), null) != null) {
                int idMarcaAux = Integer.parseInt((String) ((JSONObject) jsonObject.get(String.valueOf(idMarca))).get("idMarca"));
                String urlString = (String) ((JSONObject) jsonObject.get(String.valueOf(idMarca))).get("url");
                url.put(idMarcaAux, urlString);
            }
            /*if (marca != null) {
                sharedLinksResults = client.sharing().listSharedLinksBuilder().withPath("/files/marcas/" + marca.getMarcaCoche() + ".png").withDirectOnly(true).start();
            } else {
                sharedLinksResults = client.sharing().listSharedLinksBuilder().withPath("/files/marcas/defaultImageMarca.png").withDirectOnly(true).start();
            }

           if (sharedLinksResults.getLinks().isEmpty()) {
//                    val shareLink = DropboxClientFactory.getClient().sharing().createSharedLinkWithSettings(db_pathLower) //.getClient().sharing().getSharedLinkMetadata(db_pathLower)
//                    url = shareLink.url
                url.put(idMarca, "Imagen no disponible");
            } else {
                // Se modifica la url para obtener solo la imagen y no el html que la contiene
                String urlString = sharedLinksResults.getLinks().get(0).getUrl().replace("https://www.dropbox.com/", "https://dl.dropboxusercontent.com/");
                url.put(idMarca, urlString);

            }*/
//                dropbox_url = getShareURL(url)!!.replaceFirst("https://www".toRegex(), "https://dl")


        }

        //        Resource resource = null;
//        if (marca != null) {
//            Path filePath = Paths.get("src/main/resources/static/images/marcas").resolve("Aston Martin" + ".png").toAbsolutePath();
////            Path filePath = Paths.get("src/main/resources/static/images/marcas").resolve(marca.getMarcaCoche() + ".png").toAbsolutePath();
//            try {
//                resource = new UrlResource(filePath.toUri());
//            } catch (MalformedURLException e) {
//                log.error("Error al buscar la marca " + idMarca + ": " + e);
//                e.printStackTrace();
//            }
//        }
        return url;
    }

    @Override
    public HashMap<Integer, String> getURLModelosLogo(List<Integer> idsModelos) {
        HashMap<Integer, String> url = new HashMap<>();
        Object obj = retrieveLinkFile(LINKS_MODELOS);
        JSONObject jsonObject = (JSONObject) obj;
        jsonObject = (JSONObject) jsonObject.get("linkmodelos");
        AtomicInteger i = new AtomicInteger(2);
        JSONObject finalJsonObject = jsonObject;
        idsModelos.forEach(id -> {
            if (finalJsonObject.getOrDefault(String.valueOf(id), null) != null) {
                int idModelo = Integer.parseInt((String) ((JSONObject) finalJsonObject.get(id.toString())).get("idModelo"));
                String urlString = (String) ((JSONObject) finalJsonObject.get(id.toString())).get("url");
                url.put(idModelo, urlString);
            }
        });
        return url;
    }

    @Override
    public HashMap<Integer, String> getURLUsuario(List<Integer> idsUsuarios) {
        HashMap<Integer, String> url = new HashMap<>();
        if(idsUsuarios.get(0)==-1){ // Se quieren buscar todas las imagenes de los usuarios
            idsUsuarios = new ArrayList<>();
            List<Integer> finalIdsUsuarios = idsUsuarios;
            usuarioDAO.findAll().forEach(usuario -> finalIdsUsuarios.add(usuario.getId()));
            idsUsuarios = finalIdsUsuarios;
        }
        Object obj = retrieveLinkFile(LINKS_USUARIOS);
        JSONObject jsonObject = (JSONObject) obj;
        jsonObject = (JSONObject) jsonObject.get("linkusuarios");
        JSONObject finalJsonObject = jsonObject;
        idsUsuarios.forEach(id -> {
            if (finalJsonObject.getOrDefault(String.valueOf(id), null) != null) {
                int idUsuario = Integer.parseInt((String) ((JSONObject) finalJsonObject.get(id.toString())).get("id"));
                String urlString = (String) ((JSONObject) finalJsonObject.get(id.toString())).get("url");
                url.put(idUsuario, urlString);
            }
        });
        return url;
    }



    /**
     * Metodo para actualizar la URL del usuario en el archivo de urls local y remoto
     *
     * @param user
     */
    @Override
    public void setURLUsuario(Usuario user) {
        int idUsuario = user.getId();

        HashMap<Integer, String> url = new HashMap<>();
        Object obj = retrieveLinkFile(LINKS_USUARIOS);
        JSONObject jsonObject = (JSONObject) obj;
        JSONObject finalJsonObject = (JSONObject) jsonObject.get("linkusuarios");
        try {
            SharedLinkMetadata sharedLinkMetadata;
            sharedLinkMetadata = client.sharing().createSharedLinkWithSettings(PATH_USUARIOS + user.getImage());

            if (finalJsonObject.getOrDefault(String.valueOf(idUsuario), null) != null) {
                // Si el usuario ya tenia foto de perfil
                ((JSONObject) finalJsonObject.get(Integer.toString(idUsuario))).replace("url", sharedLinkMetadata.getUrl().replace("https://www.dropbox.com/", "https://dl.dropboxusercontent.com/"));
            } else {
                // Si es la primera foto del usuario
                String userID = String.valueOf(user.getId());
                JSONObject newObj = new JSONObject();
                newObj.put("id", userID);
                newObj.put("url", sharedLinkMetadata.getUrl().replace("https://www.dropbox.com/", "https://dl.dropboxusercontent.com/"));
                finalJsonObject.put(userID, newObj);
            }
            saveFileLocaleAndRemote(jsonObject, LINKS_USUARIOS);

        } catch (DbxException e) {
            log.error("Error al actualizar la foro del usuario en los ficheros " + LINKS_USUARIOS);
        }
    }

    private void saveFileLocaleAndRemote(JSONObject jsonObject, String filename) {
        try {
            // Zona local
            String path = String.valueOf(Paths.get("src/main/resources/static/linkfiles/").resolve(filename).toAbsolutePath());
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            bw.write(jsonObject.toJSONString());
            bw.flush();

            // Zona Remota
            MultipartFile multipartFile = new MockMultipartFile(filename, new FileInputStream(path));
            uploadFile("links", multipartFile, filename);
        } catch (IOException e) {
            log.error("No se ha podido actualizar el fichero de enlaces compartidos " + filename + ": " + e.getMessage());
        }
    }


//    @Override
//    public Resource load(String nameImage) throws MalformedURLException {
//        Resource resource;
//        HttpHeaders headers = new HttpHeaders();
//        Path filePath = this.getPath(nameImage);
//        resource = new UrlResource(filePath.toUri());
//        if (!resource.exists() && !resource.isReadable()) {
//            filePath = Paths.get("src/main/resources/static/images").resolve("defaultImage.jpg").toAbsolutePath();
//            resource = new UrlResource(filePath.toUri());
//        }
//        log.info(filePath.toString());
//        return resource;
//    }

    @Override
    public Resource loadPropietario() throws MalformedURLException {
        tipo = "usuarios";
//        return load(PROPIETARIO_NAME);
        return null;
    }

    @Override
    public Resource loadModelo(String nameImage) throws MalformedURLException {
        tipo = "modelos";
//        return load(nameImage);
        return null;
    }

//    @Override
//    public String copy(MultipartFile file) throws IOException {
//        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename().replace(" ", "");
//        Path filePath = getPath(fileName);
//        log.info(filePath.toString());
//        Files.copy(file.getInputStream(), filePath);
//        return fileName;
//    }

//    @Override
//    public boolean delete(String nameImage) {
//        if (nameImage != null && nameImage.length() > 0) {
//            Path lastfilePath = getPath(nameImage);
//            File lastFile = lastfilePath.toFile();
//            if (lastFile.exists() && lastFile.canRead()) {
//                lastFile.delete();
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public Path getPath(String nameImage) {
//        if (tipo.equals("usuarios")) {
//            return Paths.get(UPLOAD_DIR + "usuarios").resolve(nameImage).toAbsolutePath();
//        } else if (tipo.equals("modelos")) {
//            return Paths.get(UPLOAD_DIR + "modelos").resolve(nameImage).toAbsolutePath();
//        } else {
//            return null;
//        }
//    }
}
