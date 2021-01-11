package com.kevingomez.FYCBackEnd.quartzs;

import com.kevingomez.FYCBackEnd.models.DAO.Services.Impl.CocheService;
import com.kevingomez.FYCBackEnd.models.DAO.Services.Interfaces.ICocheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class SpringJobs {

    @Autowired
    ICocheService cocheService;
    private static final Logger log = LoggerFactory.getLogger(SpringJobs.class);

    @Scheduled(cron = "0 0/2 * 1/1 * ?")
//    @Scheduled(cron = "${jobs.tarea1}")
    public void calcularVolumenes() {
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


