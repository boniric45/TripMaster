package tourguide.tracker;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import tourguide.beans.user.UserBean;
import tourguide.proxies.user.UserProxy;
import tourguide.services.TourGuideServices;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Tracker extends Thread {
    private static final long trackingPollingInterval = TimeUnit.MINUTES.toSeconds(5);
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final TourGuideServices tourGuideServices;

    @Autowired
    UserProxy userService;

    private Logger logger = LoggerFactory.getLogger(Tracker.class);
    private boolean stop = false;

    public Tracker(TourGuideServices tourGuideServices) {
        this.tourGuideServices = tourGuideServices;
        executorService.submit(this);
    }

    /**
     * Assures to shut down the Tracker thread
     */
    public void stopTracking() {
        stop = true;
        executorService.shutdownNow();
    }

    // Tracker Thread
    @Override
    public void run() {
        StopWatch stopWatch = new StopWatch();
        while (true) {
            if (Thread.currentThread().isInterrupted() || stop) {
                logger.debug("Tracker stopping");
                break;
            }

            List<UserBean> users = tourGuideServices.getAllUser();
            logger.debug("Begin Tracker. Tracking " + users.size() + " users.");
            stopWatch.start();
            users.forEach(u -> tourGuideServices.trackUserLocation(u));
            stopWatch.stop();
            logger.debug("Tracker Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
            stopWatch.reset();
            try {
                logger.debug("Tracker sleeping");
                TimeUnit.SECONDS.sleep(trackingPollingInterval);
            } catch (InterruptedException e) {
                break;
            }
        }

    }
}
