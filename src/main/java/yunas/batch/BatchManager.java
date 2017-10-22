package yunas.batch;

import yunas.Batch;

/**
 * BatchManager.
 */
public interface BatchManager {

    public void add(String name,Batch executable);

    public void run(String[] args);
}