package com.kevingomez.FYCBackEnd.quartzs;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.users.FullAccount;
import com.kevingomez.FYCBackEnd.models.DAO.Services.Impl.CocheService;
import com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces.IChartService;
import com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces.ICocheService;
import com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces.IEmailService;
import com.kevingomez.FYCBackEnd.models.entity.Coches.Volumen;
import com.kevingomez.FYCBackEnd.models.entity.Coches.Volumen2Puertas;
import com.kevingomez.FYCBackEnd.models.entity.Coches.Volumen4Puertas;
import com.kevingomez.FYCBackEnd.models.entity.Coches.VolumenHatchback;
import com.kevingomez.FYCBackEnd.models.entity.Usuarios.Verificacion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

@Component
public class SpringJobs {

    @Autowired
    ICocheService cocheService;

    @Autowired
    IChartService chartService;

    private static final Logger log = LoggerFactory.getLogger(SpringJobs.class);
    private Volumen2Puertas vol2p = new Volumen2Puertas();
    private Volumen4Puertas vol4p = new Volumen4Puertas();
    private VolumenHatchback volH = new VolumenHatchback();
    private Volumen volumenBerlina = new Volumen();
    private Volumen volumenSuv = new Volumen();
    private Volumen volumenCabrio = new Volumen();
    private Volumen volumenCoupe = new Volumen();
    private Volumen volumenPickup = new Volumen();
    private Volumen volumenHatchback = new Volumen();
    private Volumen volumenMonocasco = new Volumen();
    private Volumen volumenLimusina = new Volumen();
    private Volumen volumenFunebre = new Volumen();
    private Volumen vol = new Volumen();

    @Scheduled(cron = "0 0/1 * 1/1 * ?")
    public void calcularVolumenes() {
        // Create Dropbox client
        log.info("Empezando Job Calcular Volumenes " + System.currentTimeMillis() / 1000);
        for (int i = 1; i <= 12; i++) {
            ArrayList<Double> medias = this.cocheService.generateDataVolumenCarroceria(i);
            vol2p.setVolumenMaletero(medias.get(0));
            vol2p.setVolumenHabitaculo(medias.get(1));
            vol4p.setVolumenMaletero(medias.get(2));
            vol4p.setVolumenHabitaculo(medias.get(3));
            volH.setVolumenMaletero(medias.get(4));
            volH.setVolumenHabitaculo(medias.get(5));
            vol.setVolumenHatchback(volH);
            vol.setVolumen2p(vol2p);
            vol.setVolumen4p(vol4p);

            switch (i) {
                case 1:
                    volumenBerlina = vol;
                    break;
                case 3:
                    volumenSuv = vol;
                    break;
                case 4:
                    volumenCabrio = vol;
                    break;
                case 5:
                    volumenCoupe = vol;
                    break;
                case 6:
                    volumenPickup = vol;
                    break;
                case 7:
                    volumenHatchback = vol;
                    break;
                case 8:
                    volumenMonocasco = vol;
                    break;
                case 9:
                    volumenLimusina = vol;
                    break;
                case 10:
                    volumenFunebre = vol;
                    break;
            }
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("berlina", volumenBerlina);
        map.put("suv",volumenSuv);
        map.put("cabrio",volumenCabrio);
        map.put("coupe",volumenCoupe);
        map.put("pickup",volumenPickup);
        map.put("hatchback",volumenHatchback);
        map.put("monocasco",volumenMonocasco);
        map.put("limusina",volumenLimusina);
            this.chartService.setChartVolumen(map);
        log.info("Finalizado Job Calcular Volumenes " + System.currentTimeMillis() / 1000);
    }

//    @Scheduled(fixedDelay = 2000)
//    public void scheduleJobWithDelay() {
//        try {
//            Thread.sleep(2000);
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//        }
//        log.info("SpringJob: scheduleJobWithDelay");
//    }
//
//    @Scheduled(cron = "0 * * * * ?")
//    public void scheduleJobWithCron() {
//        log.info("SpringJob: scheduleJobWithCron");
//    }
}


