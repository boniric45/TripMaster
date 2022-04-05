package tourguide.tracker;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tourguide.beans.user.UserBean;
import tourguide.services.TourGuideServices;

import java.util.List;
import java.util.concurrent.*;

public class Tracker extends Thread {
    private static final long trackingPollingInterval = TimeUnit.MINUTES.toSeconds(1);
    final int nbProcs = Runtime.getRuntime().availableProcessors(); // Number of processors available on this machine
    private final ExecutorService executorService = new ThreadPoolExecutor(nbProcs, 1000, 5L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
    private final TourGuideServices tourGuideServices;

    private final Logger logger = LoggerFactory.getLogger(Tracker.class);
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
            users.forEach(tourGuideServices::trackUserLocation);
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
