package yunas;

import yunas.batch.BatchManager;
import yunas.batch.DefaultBatchManager;
import yunas.configuration.Configuration;

/**
 * Entry Point of Yunas Framework For DefaultBatchManager.
 *
 */
public class YunasBatch  {

    private static final BatchManager manager = new DefaultBatchManager();

    private static final App instance = App.initBatch();

    public static Configuration getConfiguration() {
        return instance.getConfiguration();
    }

    public static void  run(String[] args) {
        manager.run(args);
    }

    public static void add(String name, Batch executable) {
        manager.add(name,executable);
    }

}