package com.kevingomez.FYCBackEnd.quartzs;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.users.FullAccount;
import com.kevingomez.FYCBackEnd.models.DAO.Services.Impl.CocheService;
import com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces.ICocheService;
import com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces.IEmailService;
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

@Component
public class SpringJobs {

    @Autowired
    ICocheService cocheService;

    private static final Logger log = LoggerFactory.getLogger(SpringJobs.class);


    @Scheduled(cron = "0 0/10 * 1/1 * ?")
//    @Scheduled(cron = "${jobs.tarea1}")
    public void calcularVolumenes() {
        // Create Dropbox client
        log.info("Empezando Job Calcular Volumenes " + System.currentTimeMillis() / 1000);
        for (int i = 1;i<=6;i++) {
            ArrayList<Double> medias = this.cocheService.generateDataVolumenCarroceria(i);
            log.info("mediaMaletero2p: "+ medias.get(0));
            log.info("mediaHabitaculo2p: "+ medias.get(1));
            log.info("mediaMaletero4p: "+ medias.get(2));
            log.info("mediaHabitaculo4p: "+ medias.get(3));
            log.info("mediaMaleteroH: "+ medias.get(4));
            log.info("mediaHabitaculoH: "+ medias.get(5));
        }
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


