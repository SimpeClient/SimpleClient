package simpleclient.util;

import de.jcm.discordgamesdk.Core;
import de.jcm.discordgamesdk.CreateParams;
import de.jcm.discordgamesdk.activity.Activity;
import de.jcm.discordgamesdk.activity.ActivityType;

import java.time.Instant;
import java.util.function.Supplier;

public class DiscordRPC extends Thread {
    public static DiscordRPC INSTANCE = new DiscordRPC();
    private  boolean running = true;
    private Core core = null;
    private Instant startTimestamp = null;
    private Instant ingameTimestamp = null;
    private Supplier<Activity> activitySupplier;
    private long tick = 0;

    @Override
    public void run() {
        try {
            while(running) {
                if (tick % 20 == 0) core.activityManager().updateActivity(activitySupplier.get());
                core.runCallbacks();
                Thread.sleep(50);
                tick++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init(Supplier<Activity> activitySupplier) {
        try {
            Core.initDownload();
            CreateParams params = new CreateParams();
            params.setClientID(1124616010658484335L);
            params.setFlags(CreateParams.getDefaultFlags());
            core = new Core(params);
            startTimestamp = Instant.now();
            this.activitySupplier = activitySupplier;
            start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        running = false;
        core.close();
    }

    public Instant getStartTimestamp() {
        return startTimestamp;
    }

    public Instant getIngameTimestamp() {
        return ingameTimestamp;
    }

    public void setIngameTimestamp(Instant ingameTimestamp) {
        this.ingameTimestamp = ingameTimestamp;
    }

    public static Activity activity(String line1, String line2, Instant timestamp) {
        Activity activity = new Activity();
        activity.setType(ActivityType.PLAYING);
        if (line1 != null) activity.setDetails(line1);
        if (line2 != null) activity.setState(line2);
        if (timestamp != null) activity.timestamps().setStart(timestamp);
        activity.assets().setLargeImage("simpleclient_1024");
        return activity;
    }
}