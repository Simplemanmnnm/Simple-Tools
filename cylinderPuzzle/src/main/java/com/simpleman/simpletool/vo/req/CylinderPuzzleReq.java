package com.simpleman.simpletool.vo.req;

import com.simpleman.simpletool.pojo.Puzzle;
import lombok.Data;
import lombok.NonNull;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

/**
 * 圆柱积木问题请求体
 *
 * @author l30035293
 * @since 2024-04-29
 */
@Data
public class CylinderPuzzleReq {
    /**
     * 阶数
     */
    @NotNull(message = "阶数不能为空")
    Integer order;

    /**
     * 各积木
     */
    @NotNull(message = "积木信息不能为空")
    ArrayList<Puzzle> puzzles;
}
