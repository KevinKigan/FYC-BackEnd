package com.kevingomez.FYCBackEnd.models.DAO.Services.Impl;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

import java.io.FileReader;

import com.dropbox.core.v2.files.FileMetadata;
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
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class FicherosService implements IFicherosService {

    private static final Logger log = LoggerFactory.getLogger(FicherosService.class);

    @Autowired
    private IMarcaDAO marcaDAO;

    @Value("${accessTokenDropbox}")
    private String ACCESS_TOKEN;

    @Value("${uploadPath.Image}")
    private String UPLOAD_DIR;

    @Value("${propietario.Image}")
    private String PROPIETARIO_NAME;

    private final String LINKS_MARCAS = "sharedlinksMarcas.json";
    private final String LINKS_MODELOS = "sharedlinksModelos.json";
    private static String tipo;


    private Object retrieveLinkFile(String nombreFichero) {
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox").build();
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
        JSONParser parser = new JSONParser();
        File sharelinksMarcas = new File(String.valueOf(Paths.get("src/main/resources/static/linkfiles/").resolve(nombreFichero).toAbsolutePath()));
        if (!sharelinksMarcas.exists()) {
            log.info("Se crea el fichero " + nombreFichero);
            String pathdir = String.valueOf(Paths.get("src/main/resources/static/linkfiles/").toAbsolutePath());
            File dir = new File(pathdir);
            if (!dir.exists()) dir.mkdirs();
            pathdir += "/" + nombreFichero;
            OutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(pathdir);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                FileMetadata metadata = client.files()
                        .downloadBuilder("/files/links/" + nombreFichero)
                        .download(outputStream);
            } catch (DbxException | IOException e) {
                e.printStackTrace();
            }
            log.info("Fichero " + nombreFichero + " descargado");
        }
        try {
            return parser.parse(new FileReader(String.valueOf(Paths.get("src/main/resources/static/linkfiles/").resolve(nombreFichero).toAbsolutePath())));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
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

        //TODO para subir con dropbox
//        FullAccount account = null;

//            account = client.users().getCurrentAccount();
//            System.out.println(account.getName().getDisplayName());
//            ListFolderResult result = client.files().listFolder("");
//            while (true) {
//                for (Metadata metadata : result.getEntries()) {
//                    System.out.println(metadata.getPathLower());
//                }
//
//                if (!result.getHasMore()) {
//                    break;
//                }
//
//                result = client.files().listFolderContinue(result.getCursor());
//            }
//            try (InputStream in = new FileInputStream("src/main/resources/static/images/marcas/Ford.png")) {
//                FileMetadata metadata = client.files().uploadBuilder("/files/marcas/Ford.png")
//                        .uploadAndFinish(in);

//            } catch (IOException e) {
//                e.printStackTrace();
//            }

        //TODO para descargar con dropbox
//            String localPath = "src/main/resources/static/images/marcas/Aston Martin.png";
//            OutputStream outputStream = new FileOutputStream(localPath);
//            FileMetadata metadata = client.files()
//                    .downloadBuilder("/files/marcas/"+marca.getMarcaCoche() + ".png")
//                    .download(outputStream);
//            log.info("Descargando imagen");

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
            jsonObject.forEach((idMarcaFE,object) -> {
                url.put(Integer.valueOf(idMarcaFE.toString()), ((JSONObject)object).get("url").toString());
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
                if(jsonObject.getOrDefault(String.valueOf(idMarca), null)!=null) {
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
            if(finalJsonObject.getOrDefault(String.valueOf(id), null)!=null) {
                int idModelo = Integer.parseInt((String) ((JSONObject) finalJsonObject.get(id.toString())).get("idModelo"));
                String urlString = (String) ((JSONObject) finalJsonObject.get(id.toString())).get("url");
                url.put(idModelo, urlString);
            }
        });
//        jsonArray.forEach(modelo -> {
//            JSONObject jsonObject2 = (JSONObject) modelo;
//            //TODO revisar esto, algo no funciona bien
////            if(jsonObject2.getOrDefault()containsValue(idsModelos.get(i.get()).toString())){
//            System.out.println(idsModelos.get(i.get()));
//            jsonObject2.getOrDefault("idModelo", idsModelos.get(i.get()).toString());
//            Object m = new String[]{"idModelo", "1"};
//            System.out.println(jsonArray.indexOf(m));
////            jsonObject2.get("idModelo").;
//            url.put(Integer.valueOf(jsonObject2.get("idModelo").toString()), jsonObject2.get("url").toString());
////            }
//            i.getAndIncrement();
////            if()
//        });
        return url;
    }


    @Override
    public Resource load(String nameImage) throws MalformedURLException {
        Resource resource;
        HttpHeaders headers = new HttpHeaders();
        Path filePath = this.getPath(nameImage);
        resource = new UrlResource(filePath.toUri());
        if (!resource.exists() && !resource.isReadable()) {
            filePath = Paths.get("src/main/resources/static/images").resolve("defaultImage.jpg").toAbsolutePath();
            resource = new UrlResource(filePath.toUri());
        }
        log.info(filePath.toString());
        return resource;
    }
    @Override
    public Resource loadPropietario() throws MalformedURLException {
        tipo = "usuarios";
        return load(PROPIETARIO_NAME);
    }
    @Override
    public Resource loadModelo(String nameImage) throws MalformedURLException {
        tipo = "modelos";
        return load(nameImage);
    }

    @Override
    public String copy(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename().replace(" ", "");
        Path filePath = getPath(fileName);
        log.info(filePath.toString());
        Files.copy(file.getInputStream(), filePath);
        return fileName;
    }

    @Override
    public boolean delete(String nameImage) {
        if(nameImage != null && nameImage.length() > 0){
            Path lastfilePath = getPath(nameImage);
            File lastFile = lastfilePath.toFile();
            if(lastFile.exists() && lastFile.canRead()){
                lastFile.delete();
            }
        }
        return false;
    }

    @Override
    public Path getPath(String nameImage) {
        if(tipo.equals("usuarios")){
            return Paths.get(UPLOAD_DIR+"usuarios").resolve(nameImage).toAbsolutePath();
        }else if(tipo.equals("modelos")){
            return Paths.get(UPLOAD_DIR+"modelos").resolve(nameImage).toAbsolutePath();
        }else{
            return null;
        }
    }
}
