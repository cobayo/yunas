package yunas.batch;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yunas.Batch;

import java.util.HashMap;
import java.util.Map;

import static java.lang.System.exit;

/**
 * Manage Batch Modules.
 *
 */
public class DefaultBatchManager implements BatchManager {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultBatchManager.class);

    private Map<String,Batch> modules = new HashMap<>();

    @Override
    public void add(String name, Batch executable) {
        modules.put(name,executable);
    }

    @Override
    public void run(String[] args) {

        try {
            String name = System.getProperty("yunas.batch");
            if (!modules.containsKey(name)) {
                LOG.error("NOT FOUND module: " + name);
                return;
            }
            modules.get(name).action(args);
        } catch (Exception e) {
             LOG.error(e.getMessage());
             exit(1);
        }
    }

}