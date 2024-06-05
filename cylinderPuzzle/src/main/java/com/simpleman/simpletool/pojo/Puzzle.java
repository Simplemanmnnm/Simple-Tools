package com.simpleman.simpletool.pojo;

import lombok.Data;

import java.util.ArrayList;

/**
 * 单块积木
 *
 * @author l30035293
 * @since 2024-04-29
 */
@Data
public class Puzzle extends PuzzleBase {
    /**
     * 可用形状的坐标，正、反
     */
    ArrayList<ArrayList<Coordinate>> availableForms;

    /**
     * 此积木是否在遍历中被用到了
     */
    boolean isUsed;
}
