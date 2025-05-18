package tw.com.rex.txt2epub.builder;

import tw.com.rex.txt2epub.builder.helper.GridPositionTracker;

import java.awt.*;

/**
 * GridBagConstraints 建構器
 */
public class GBCBuilder {

    private final GridBagConstraints gridBagConstraints;

    /**
     * @param gridX   X 軸
     * @param gridY   Y 軸
     * @param tracker 位置追蹤器
     */
    public GBCBuilder(int gridX, int gridY, GridPositionTracker tracker) {
        if (tracker.isUsed(gridX, gridY)) {
            throw new IllegalArgumentException("位置 (" + gridX + "," + gridY + ") 已被使用！");
        }
        tracker.markUsed(gridX, gridY);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = gridX;
        gridBagConstraints.gridy = gridY;
        gridBagConstraints.insets = new Insets(0, 0, 0, 0); // 預設外距
    }

    public GBCBuilder gridWidth(int width) {
        gridBagConstraints.gridwidth = width;
        return this;
    }

    public GBCBuilder gridHeight(int height) {
        gridBagConstraints.gridheight = height;
        return this;
    }

    public GBCBuilder weight(double weightx, double weighty) {
        gridBagConstraints.weightx = weightx;
        gridBagConstraints.weighty = weighty;
        return this;
    }

    public GBCBuilder fill(int fillType) {
        gridBagConstraints.fill = fillType;
        return this;
    }

    public GBCBuilder anchor(int anchorType) {
        gridBagConstraints.anchor = anchorType;
        return this;
    }

    public GBCBuilder insets(int top, int left, int bottom, int right) {
        gridBagConstraints.insets = new Insets(top, left, bottom, right);
        return this;
    }

    public GBCBuilder ipad(int ipadX, int ipadY) {
        gridBagConstraints.ipadx = ipadX;
        gridBagConstraints.ipady = ipadY;
        return this;
    }

    public GridBagConstraints build() {
        return gridBagConstraints;
    }

}