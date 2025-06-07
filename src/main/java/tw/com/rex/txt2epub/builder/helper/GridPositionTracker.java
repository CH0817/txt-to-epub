package tw.com.rex.txt2epub.builder.helper;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * {@link GridBagConstraints} 位置追蹤器
 */
public class GridPositionTracker {

    private final Set<String> usedPositions = new HashSet<>();

    /**
     * {@link GridBagConstraints} 的位置是否被使用
     *
     * @param x X 軸
     * @param y Y 軸
     * @return 是否被使用
     */
    public boolean isUsed(int x, int y) {
        return usedPositions.contains(key(x, y));
    }

    /**
     * 將位置標記為已使用
     *
     * @param x X 軸
     * @param y Y 軸
     */
    public void markUsed(int x, int y) {
        usedPositions.add(key(x, y));
    }

    /**
     * 取得位置的 key
     *
     * @param x X 軸
     * @param y Y 軸
     * @return key
     */
    private String key(int x, int y) {
        return x + "," + y;
    }

}
