package com.simpleman.simpletool.vo.resp;

import com.simpleman.simpletool.pojo.PuzzleBase;
import lombok.Data;

import java.util.ArrayList;

/**
 * 描述
 *
 * @author l30035293
 * @since 2024-04-29
 */
@Data
public class CylinderPuzzleResp {
    /**
     * 是否有解
     */
    boolean hasSolution;

    /**
     * 圆柱积木问题的解
     * 积木信息 + 实际坐标 的集合
     */
    ArrayList<PuzzleBase> solution;
}
