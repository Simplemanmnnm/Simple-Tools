package com.simpleman.simpletool.controller;

import com.simpleman.simpletool.common.result.Result;
import com.simpleman.simpletool.service.CylinderPuzzleService;
import com.simpleman.simpletool.vo.req.CylinderPuzzleReq;

import com.simpleman.simpletool.vo.resp.CylinderPuzzleResp;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 描述
 *
 * @author l30035293
 * @since 2023-08-24
 */
@RestController
@RequestMapping("/testUtil")
public class TestUtilController {

    @Resource
    private CylinderPuzzleService cylinderPuzzleService;

    @PostMapping("/cylinderPuzzle")
    public Result<CylinderPuzzleResp> cylinderPuzzle(@Valid @RequestBody CylinderPuzzleReq cylinderPuzzleReq) {
        return Result.success(cylinderPuzzleService.solveCylinderPuzzle(cylinderPuzzleReq));
    }
}