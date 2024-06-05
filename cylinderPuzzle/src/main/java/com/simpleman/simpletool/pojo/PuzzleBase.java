package com.simpleman.simpletool.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * 单块积木
 *
 * @author l30035293
 * @since 2024-04-29
 */
@Data
@NoArgsConstructor
public class PuzzleBase {
    /**
     * 是否为中间积木
     * true:中间积木 false:边缘积木
     */
    Boolean isCentral;

    /**
     * 积木的序号
     */
    int sequenceNum;

    /**
     * 积木的颜色
     */
    String color;

    /**
     * 积木在平面上的坐标分布
     */
    ArrayList<Coordinate> coordinates;

    public PuzzleBase(PuzzleBase newPuzzle) {
        this.color = newPuzzle.getColor();
        this.isCentral = newPuzzle.getIsCentral();
        this.sequenceNum = newPuzzle.getSequenceNum();
        this.coordinates = newPuzzle.getCoordinates();
    }

}
